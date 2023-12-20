package com.xiang.service;


import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.User;

public interface LoginUserService {
    ResponseResult login(User user);

    ResponseResult logout();
}
