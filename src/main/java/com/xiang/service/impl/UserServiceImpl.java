package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.vo.PageVo;
import com.xiang.domain.vo.UserInfoVo;
import com.xiang.domain.vo.UserLoginVo;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.exception.SystemException;
import com.xiang.mapper.UserMapper;
import com.xiang.service.UserService;
import com.xiang.domain.entity.User;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.JwtUtils;
import com.xiang.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
* @author chenwentao
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-12-20 13:50:11
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult register(User user) {
        //非空判断
        if (! StringUtils.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.NEED_USERNAME);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.NEED_PASSWORD);
        }
        if (!StringUtils.hasText(user.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.NEED_PHONENUMBER);
        }

        //重复判断
        if(usernameExist(user.getUsername())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(phonenumberExist(user.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }

        String encodePasswd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePasswd);
        save(user);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult updateUser(User user) {
        User user3 = getById(user);
        if (user3 == null) {
            throw new SystemException(AppHttpCodeEnum.NO_SUCH_USER);
        }
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getUsername,user.getUsername());
        User user1 = getOne(queryWrapper1);
        if (user1 != null && !Objects.equals(user1.getId(), user.getId())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getPhonenumber,user.getPassword());
        User user2 = getOne(queryWrapper2);
        if (user2 != null && !Objects.equals(user2.getId(), user.getId())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        updateById(user);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        User user3 = getById(id);
        if (user3 == null) {
            throw new SystemException(AppHttpCodeEnum.NO_SUCH_USER);
        }
        removeById(id);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult myInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.ok(userInfoVo);
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, String username, String phonenumber, String sex) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(username),User::getUsername,username);
        queryWrapper.like(Objects.nonNull(phonenumber),User::getPhonenumber,phonenumber);
        queryWrapper.eq("0".equals(sex) || "1".equals(sex) || "2".equals(sex),User::getSex,sex);
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<UserInfoVo> userInfoVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserInfoVo.class);
        PageVo pageVo = new PageVo(userInfoVos, page.getTotal());
        return ResponseResult.ok(pageVo);
    }


    private boolean phonenumberExist(String phonenumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phonenumber);
        return getOne(queryWrapper) != null;
    }

    private boolean usernameExist(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        return getOne(queryWrapper) != null;
    }
}




