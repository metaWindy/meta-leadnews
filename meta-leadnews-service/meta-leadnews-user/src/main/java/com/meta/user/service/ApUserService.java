package com.meta.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.model.common.dtos.ResponseResult;
import com.meta.model.user.dtos.LoginDto;
import com.meta.model.user.pojos.ApUser;
import org.springframework.stereotype.Service;

public interface ApUserService extends IService<ApUser> {
    /**
     * app端登录功能
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);
}
