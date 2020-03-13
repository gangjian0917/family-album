package com.vbeesoft.familyalbum.controller;

import com.vbeesoft.familyalbum.common.json.Json;
import com.vbeesoft.familyalbum.core.BaseCode;
import com.vbeesoft.familyalbum.model.LoginBean;
import com.vbeesoft.familyalbum.model.dataobject.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RedisTemplate<String, String> stringTemplate;

    private ValueOperations<String, String> opsForValue;

    public static final String account_pre_key = "account_pre_key_";

    public static final String last_userid_key = "last_userid_key";

    @RequestMapping("/register2.php")
    public LoginBean register2(String account, String password, String phoneNumber) {
        LOG.info("register2 phoneNumber:{}", phoneNumber);
        LoginBean result = new LoginBean();
        opsForValue = stringTemplate.opsForValue();

        String key = account_pre_key + phoneNumber;
        String acc = opsForValue.get(key);
        if (acc != null) {
            result.setResult(BaseCode.account_already_exists_error);
            return result;
        }

        String userId = genUserId();
        UserDO userDO = new UserDO(account, password, phoneNumber, userId);

        opsForValue.set(key, Json.toJson(userDO));
        result.setResult(BaseCode.register_success);
        result.setUserID(userId);
        LOG.info("register2 result:{}", result);
        return result;
    }

    @RequestMapping("/login2.php")
    public LoginBean login2(String phoneNumber, String password) {
        LOG.info("login2 phoneNumber:{}", phoneNumber);

        LoginBean result = new LoginBean();
        opsForValue = stringTemplate.opsForValue();

        String key = account_pre_key + phoneNumber;
        String acc = opsForValue.get(key);
        if (acc == null) {
            result.setResult(BaseCode.account_or_password_error);
            return result;
        }

        UserDO userDO = Json.toObject(acc, UserDO.class);
        if (userDO == null || !password.equals(userDO.getPassword())) {
            result.setResult(BaseCode.account_already_exists_error);
            return result;
        }

        result.setResult(BaseCode.login_success);
        result.setUserID(userDO.getUserID());
        LOG.info("login2 result:{}", result);
        return result;
    }

    private String genUserId() {
        opsForValue = stringTemplate.opsForValue();
        String id = opsForValue.get(last_userid_key);
        if (id == null) {
            return 1 + "";
        }
        return (Long.parseLong(id) + 1) + "";
    }
}
