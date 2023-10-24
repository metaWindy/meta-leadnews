package com.meta.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.model.common.dtos.ResponseResult;
import com.meta.model.wemedia.pojos.WmChannel;
import com.meta.wemedia.mapper.WmChannelMapper;
import com.meta.wemedia.service.WmChannelService;
import org.springframework.stereotype.Service;

@Service
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {
    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }
}

