package com.meta.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.model.common.dtos.ResponseResult;
import com.meta.model.wemedia.dtos.WmNewsDto;
import com.meta.model.wemedia.dtos.WmNewsPageReqDto;
import com.meta.model.wemedia.pojos.WmNews;
import com.meta.wemedia.mapper.WmNewsMapper;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 查询文章
     * @param dto
     * @return
     */
    ResponseResult findAll(WmNewsPageReqDto dto);

    /**
     *  发布文章或保存草稿
     * @param dto
     * @return
     */
    ResponseResult submitNews(WmNewsDto dto);
}
