package com.xiang.service.impl;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.LoginUser;
import com.xiang.domain.entity.User;
import com.xiang.domain.vo.UserInfoVo;
import com.xiang.domain.vo.UserLoginVo;
import com.xiang.service.LoginUserService;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginUserServiceImpl implements LoginUserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        LoginUser loginUser = ((LoginUser) authenticate.getPrincipal());
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtils.createJwt(userId);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo userLoginVo = new UserLoginVo(userInfoVo, jwt);
        return ResponseResult.ok(userLoginVo);
    }

    @Override
    public ResponseResult logout() {
        return ResponseResult.ok();
    }

}
