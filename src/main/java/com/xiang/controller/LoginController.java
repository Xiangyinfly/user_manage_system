package com.xiang.controller;

import com.xiang.annotation.SystemLog;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.LoginUserDto;
import com.xiang.domain.entity.User;
import com.xiang.service.LoginUserService;
import com.xiang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private LoginUserService loginUserService;

    @SystemLog(businessName = "登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginUserDto loginUserDto) {
        User user = BeanCopyUtils.copyBean(loginUserDto, User.class);
        return loginUserService.login(user);
    }

    @SystemLog(businessName = "退出登录")
    @PostMapping("/logout")
    public ResponseResult logout() {
        return loginUserService.logout();
    }
}
