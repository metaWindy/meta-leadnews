package com.meta.wemedia.controller.v1;

import com.meta.model.common.dtos.ResponseResult;
import com.meta.model.wemedia.dtos.WmNewsDto;
import com.meta.model.wemedia.dtos.WmNewsPageReqDto;
import com.meta.wemedia.service.WmNewsAutoScanService;
import com.meta.wemedia.service.WmNewsService;
import io.lettuce.core.dynamic.annotation.Param;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {

    @Autowired
    private WmNewsService wmNewsService;

    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto){
        return  wmNewsService.findAll(dto);
    }

    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto){
        return wmNewsService.submitNews(dto);
    }

}
