package com.meta.apis.article.fallback;

import com.meta.apis.article.IArticleClient;
import com.meta.model.article.dtos.ArticleDto;
import com.meta.model.common.dtos.ResponseResult;
import com.meta.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class IArticleClientFallback implements IArticleClient {
    @Override
    public ResponseResult saveArticle(ArticleDto dto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
    }
}

