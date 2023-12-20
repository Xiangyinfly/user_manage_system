package com.xiang.filter;

import com.xiang.domain.ResponseResult;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.utils.JacksonUtils;
import com.xiang.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request,response);
            return;
        }

        Claims claims = null;
        try {
            claims = JwtUtils.parseJwt(token);
        } catch (Exception e) {
            ResponseResult error = ResponseResult.error(AppHttpCodeEnum.NEED_LOGIN.getCode(),AppHttpCodeEnum.NEED_LOGIN.getMsg());
            String errorString = JacksonUtils.toJsonString(error);
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(errorString);
        }


        //保存在security context中
        String userIdString = claims.getSubject();
        long userId = Long.parseLong(userIdString);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }
}
