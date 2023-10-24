package com.meta.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.model.common.dtos.ResponseResult;
import com.meta.model.wemedia.dtos.WmLoginDto;
import com.meta.model.wemedia.pojos.WmUser;

public interface WmUserService extends IService<WmUser> {
    ResponseResult login(WmLoginDto dto);
}
