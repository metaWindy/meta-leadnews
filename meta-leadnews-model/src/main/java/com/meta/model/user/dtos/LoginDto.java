package com.meta.model.user.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;
}