package com.meta.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.model.article.dtos.ArticleDto;
import com.meta.model.article.dtos.ArticleHomeDto;
import com.meta.model.article.pojos.ApArticle;
import com.meta.model.common.dtos.ResponseResult;

public interface ApArticleService extends IService<ApArticle> {

    ResponseResult load(ArticleHomeDto dto, Short type);

    ResponseResult saveArticle(ArticleDto dto);
}
