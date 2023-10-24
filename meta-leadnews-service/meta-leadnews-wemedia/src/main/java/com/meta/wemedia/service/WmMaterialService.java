package com.meta.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.model.common.dtos.ResponseResult;
import com.meta.model.wemedia.dtos.WmMaterialDto;
import com.meta.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {

    ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 素材列表查询
     * @param dto
     * @return
     */
    ResponseResult findList( WmMaterialDto dto);
}
