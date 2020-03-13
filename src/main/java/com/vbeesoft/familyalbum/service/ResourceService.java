package com.vbeesoft.familyalbum.service;

import com.vbeesoft.familyalbum.common.StringUtils;
import com.vbeesoft.familyalbum.common.video.VideoUtils;
import com.vbeesoft.familyalbum.controller.ResourceController;
import com.vbeesoft.familyalbum.model.VideoSnapshootBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ResourceService {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);

    @Autowired
    private RedisTemplate<String, String> stringTemplate;

    private ValueOperations<String, String> opsForValue;

    public VideoSnapshootBean video_snap(String filePath) {
        LOG.info("video_snap filePath:{} " , filePath);
        VideoSnapshootBean result = new VideoSnapshootBean();
        if (StringUtils.isEmpty(filePath)) {
            result.setCode(10000);
            result.setMessage("参数为空");
            return result;
        }

        filePath = ResourceController.res_root_path + filePath;
        LOG.info("filePath:{}", filePath);
        opsForValue = stringTemplate.opsForValue();
        String redisKey = filePath;
        String json = opsForValue.get(redisKey);

//        if (json != null) {
//            result.setSnapUrl(json);
//            return result;
//        }

        String png = filePath + ".png";
        File file = new File(png);
        if (file == null || !file.exists()) {
            result.setCache(VideoSnapshootBean.calculate_now);
            try {
                png = VideoUtils.videoImage(filePath, 6);
            } catch (Exception e) {
                LOG.error("get video image error");
                result.setCode(10000);
                result.setMessage("计算快照出错");
                return result;
            }
        }

        if (file.exists() && !file.isDirectory()) {
            result.setSnapUrl(png);
            return result;
        }

        opsForValue.set(redisKey, png);
        LOG.info("video_snap result:{} ", result);
        return result;
    }
}
