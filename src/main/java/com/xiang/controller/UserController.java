package com.xiang.controller;

import com.xiang.annotation.SystemLog;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.RegisterUserDto;
import com.xiang.domain.dto.UpdateUserDto;
import com.xiang.domain.entity.User;
import com.xiang.service.UserService;
import com.xiang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @SystemLog(businessName = "注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterUserDto registerUserDto) {
        User user = BeanCopyUtils.copyBean(registerUserDto, User.class);
        return userService.register(user);
    }

    @SystemLog(businessName = "修改用户信息")
    @PutMapping
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto) {
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        return userService.updateUser(user);
    }

    @SystemLog(businessName = "删除用户")
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @SystemLog(businessName = "查询我的信息")
    @GetMapping("/myInfo")
    public ResponseResult getMyInfo() {
        return userService.myInfo();
    }

    @SystemLog(businessName = "查询用户列表")
    @GetMapping("/userList")
    public ResponseResult getUserList(Integer pageNum,Integer pageSize,String username,String phonenumber,String sex) {
        return userService.getUserList(pageNum,pageSize,username,phonenumber,sex);
    }



}
