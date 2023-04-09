package com.kamikakushipage.controller;

import com.kamikakushipage.entity.User;
import com.kamikakushipage.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("index")
    public String index(){
        return "index";
    }

    @PostMapping("creat")
    public Map<String,Object> creatAccount(User user) {
        return userService.creatAccount(user);
    }

    @GetMapping("activation")
    public String activateAccount(String confirmCode) {
        Map<String,Object> resultMap = userService.activateAccount(confirmCode);
        if (resultMap.containsValue(200)) {
            return "激活成功";
        } else {
            return "激活失败";
        }


    }
}
