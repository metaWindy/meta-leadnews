package com.meta.model.wemedia.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * WM 登录 DTO
 *
 * @author 沃是泥椰椰
 * @date 2023/09/26
 */
@Data
public class WmLoginDto {

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;
}

