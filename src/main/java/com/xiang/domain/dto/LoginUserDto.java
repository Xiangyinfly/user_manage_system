package com.xiang.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
