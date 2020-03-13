package com.vbeesoft.familyalbum.controller;

import com.vbeesoft.familyalbum.common.StringUtils;
import com.vbeesoft.familyalbum.common.date.DateTimeUtil;
import com.vbeesoft.familyalbum.common.json.Json;
import com.vbeesoft.familyalbum.common.video.VideoUtils;
import com.vbeesoft.familyalbum.core.BaseCode;
import com.vbeesoft.familyalbum.model.AlbumBean;
import com.vbeesoft.familyalbum.model.RecycleBinBean;
import com.vbeesoft.familyalbum.model.ResultBean;
import com.vbeesoft.familyalbum.model.VideoSnapshootBean;
import com.vbeesoft.familyalbum.service.ResourceService;
import org.bytedeco.javacv.FrameGrabber;
import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

@RestController
public class ResourceController {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);


    @Autowired
    private RedisTemplate<String, String> stringTemplate;

    @Autowired
    private ResourceService resourceService;

    private ValueOperations<String, String> opsForValue;


    public static final String userid_res_pre_key = "userid_res_pre_key_";

    public static String res_root_url = "/data/cloudRes/";
    public static String res_root_path = "/www/wwwroot/files/cloudRes/";
    public static String res_recycle_bin_dir = "recycle/";

    static {
        String osName = System.getProperty("os.name");
        if (osName != null && "Mac OS X".equals(osName)) {
            res_root_path = "/Users/jamesding/data/cloudRes/";
        }
    }

    @RequestMapping("/cloudimg.php")
    public AlbumBean cloudImg(String userID) {
        LOG.info("cloudimg userID:{} " + userID);
        String redisKey = userid_res_pre_key + userID;
        AlbumBean result = new AlbumBean();
        if (StringUtils.isEmpty(userID)) {
            return result;
        }
        opsForValue = stringTemplate.opsForValue();
        String json = opsForValue.get(redisKey);
        String path = getUserResPath(userID, res_root_path);
        LOG.info("cloudImg path " + path);
        File file = new File(path);

        if (file == null || !file.exists()) {
            result.setResult_code(BaseCode.account_already_exists_error);
        }
        if (!file.isDirectory()) {
            result.setResult_code(BaseCode.account_already_exists_error);
        }

        Set<String> files = new HashSet<>();
        scanFile(file, files);
        Map<String, ResultBean> map = new HashMap<>();

        ResultBean resultBean = new ResultBean();
        String dateStr = null;
        resultBean.setDate(DateTimeUtil.getDateStr(new Date(file.lastModified()), DateTimeUtil.PATTERN_LONG_LONG));

        for (String f : files) {
            dateStr = DateTimeUtil.getDateStr(new Date(new File(f).lastModified()), DateTimeUtil.PATTERN_A);
            ResultBean bean = map.get(dateStr);
            if (bean == null) {
                bean = new ResultBean();
                bean.setDate(dateStr);
            }
            List<String> urls = bean.getImgUrls();
            if (urls == null) {
                urls = new ArrayList<>();
            }
            f = f.replace("/Users/jamesding", "");
            LOG.info("f {}", f);
            f = getVideoSnap(f);
            LOG.info("f {}", f);
            f = f.replace("/www/wwwroot/files", "/data");
            LOG.info("f {}", f);
            urls.add(f);
            bean.setImgUrls(urls);
            map.put(dateStr, bean);
        }

        List<ResultBean> resultBeans = new ArrayList<>();
        for (String key : map.keySet()) {
            resultBeans.add(map.get(key));
        }

        result.setResult(resultBeans);
        opsForValue.set(redisKey, Json.toJson(result));
        LOG.info("cloudImg result: " + Json.toJson(result));
        return result;
    }

    private String getVideoSnap(String file) {
        String rtn = file;
        if (file.toLowerCase().endsWith(".mp4")) {
            VideoSnapshootBean videoSnapshootBean = resourceService.video_snap(file);
            LOG.info("getVideoSnap {}", videoSnapshootBean);
            if (videoSnapshootBean == null||StringUtils.isEmpty(videoSnapshootBean.getSnapUrl())) {
                return file;
            }
            return videoSnapshootBean.getSnapUrl();
        }
        return rtn;
    }

    private String getUserResPath(String userID, String res_root_path) {
        return res_root_path + userID;
    }

    private String getUserResRecyclePath(String userID) {
        return res_root_path + res_recycle_bin_dir + userID;
    }

    @RequestMapping("/video_snap.php")
    public VideoSnapshootBean video_snap(String filePath) {
        LOG.info("video_snap filePath:{} ", filePath);
        VideoSnapshootBean result = resourceService.video_snap(filePath);
        return result;
    }

    private static void scanFile(File file, Set<String> files) {
        File[] all = file.listFiles();

        for (File f : all) {
            if (f.isDirectory()) {    //若是目录，则递归打印该目录下的文件
                scanFile(f, files);
            }
            if (f.isFile() && !f.isHidden()) {
                files.add(f.getAbsolutePath());
            }
        }
    }

    @RequestMapping("/recycle_bin.php")
    public RecycleBinBean recycleBin(String userID) {
        LOG.info("recycle_bin userID:{} " + userID);
        String redisKey = userid_res_pre_key + userID;
        RecycleBinBean result = new RecycleBinBean();
        if (StringUtils.isEmpty(userID)) {
            return result;
        }
        opsForValue = stringTemplate.opsForValue();
        String json = opsForValue.get(redisKey);
        String path = getUserResRecyclePath(userID);
        LOG.info("recycle_bin path " + path);
        File file = new File(path);

        if (file == null || !file.exists()) {
            result.setResult_code(BaseCode.account_already_exists_error);
        }
        if (!file.isDirectory()) {
            result.setResult_code(BaseCode.account_already_exists_error);
        }

        Set<String> files = new HashSet<>();
        scanFile(file, files);
        List<String> urls = new ArrayList<>();

        for (String f : files) {
            f = f.replace("/Users/jamesding", "");
            f = f.replace("/www/wwwroot/files", "/data");
            urls.add(f);
        }
        result.setResult(urls);
        opsForValue.set(redisKey, Json.toJson(result));
        LOG.info("recycle_bin result: " + Json.toJson(result));
        return result;
    }

    public static void main(String[] args) {
        File file = new File("/Users/jamesding/data/cloudRes/1");
        Set<String> files = new HashSet<>();
        scanFile(file, files);
        for (String f : files) {
            System.out.println("f = " + f);
        }
    }
}
