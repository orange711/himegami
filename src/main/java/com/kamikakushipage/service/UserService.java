package com.kamikakushipage.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.kamikakushipage.entity.User;
import jakarta.annotation.Resource;
import org.apache.catalina.security.SecurityUtil;

import java.security.Security;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kamikakushipage.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service

public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MailService mailService;

    public Map<String, Object> creatAccount(User user) {

        User userMail = userMapper.selectUserByEmailDuplicate(user.getEmail());


        String confirmCode = IdUtil.getSnowflake(1, 1).nextIdStr();
        String salt = RandomUtil.randomString(6);
        String md5Pwd = SecureUtil.md5(user.getPassword() + salt);

        LocalDateTime ldt = LocalDateTime.now().plusDays(1);

        user.setSalt(salt);
        user.setPassword(md5Pwd);
        user.setConfirmCode(confirmCode);
        user.setActivation(ldt);
        user.setIsValid((byte)0);

        HashMap<String, Object> resultMap = new HashMap<>();

        if (userMail != null && userMail.getEmail().equals( user.getEmail())) {
            resultMap.put("code",400);
            resultMap.put("message","邮箱已经被预约");
            return resultMap;
        }

        int result = userMapper.insertUser(user);

        if (result > 0) {
//            发送邮件
            String activationUrl = "http://localhost:8080/activation?confirmCode=" + confirmCode;
            mailService.sendMailToUser(activationUrl,user.getEmail());
            resultMap.put("code",200);
            resultMap.put("message","预约成功,前往邮箱激活");

        }else {
            resultMap.put("code",400);
            resultMap.put("message","预约失败");
        }

        return resultMap;
    }

    public Map<String, Object> activateAccount(String confirmCode) {
        HashMap<String, Object> resultMap = new HashMap<>();
        User user = userMapper.selectUserByConfirmCode(confirmCode);
        if (user == null) {
            resultMap.put("code",400);
            resultMap.put("message","激活失败");
            return resultMap;
        }
        boolean after = LocalDateTime.now().isAfter(user.getActivation());
        if (after) {
            resultMap.put("code",400);
            resultMap.put("message","链接失效");
            return resultMap;
        }

        int result = userMapper.updateUserByConfirmCode(confirmCode);
        if (result > 0) {
            resultMap.put("code",200);
            resultMap.put("message","激活成功");
            return resultMap;
        } else {
            resultMap.put("code",400);
            resultMap.put("message","激活失败");
            return resultMap;
        }


    }
}
