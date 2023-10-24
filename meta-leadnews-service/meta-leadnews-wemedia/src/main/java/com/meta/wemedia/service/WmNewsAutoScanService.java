package com.meta.wemedia.service;

import com.meta.model.common.dtos.ResponseResult;

public interface WmNewsAutoScanService {

    /**
     * 自媒体文章审核
     * @param id  自媒体文章id
     */
    void autoScanWmNews(Integer id);
    ResponseResult autoScanWmNewsTest(Integer id);
}
