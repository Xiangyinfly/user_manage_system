package com.xiang.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiang.domain.ResponseResult;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.utils.JacksonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        ResponseResult result = null;
        if (authException instanceof BadCredentialsException) {
            result = ResponseResult.error(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            result = ResponseResult.error(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            result = ResponseResult.error(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }

        String errorString = JacksonUtils.toJsonString(result);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(errorString);
    }
}
