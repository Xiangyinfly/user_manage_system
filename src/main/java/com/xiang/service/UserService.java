package com.xiang.service;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenwentao
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2023-12-20 13:50:11
*/
public interface UserService extends IService<User> {

    ResponseResult register(User user);

    ResponseResult updateUser(User user);

    ResponseResult deleteUser(Long id);

    ResponseResult myInfo();

    ResponseResult getUserList(Integer pageNum, Integer pageSize, String username, String phonenumber, String sex);
}
