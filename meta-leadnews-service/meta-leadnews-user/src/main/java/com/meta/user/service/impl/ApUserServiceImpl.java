package com.meta.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.model.common.dtos.ResponseResult;
import com.meta.model.common.enums.AppHttpCodeEnum;
import com.meta.model.user.dtos.LoginDto;
import com.meta.model.user.pojos.ApUser;
import com.meta.user.mapper.ApUserMapper;
import com.meta.user.service.ApUserService;
import com.meta.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {
    @Override
    public ResponseResult login(LoginDto dto) {
        //1.正常登录 用户名和密码
        if(StringUtils.isNoneBlank(dto.getPhone()) && StringUtils.isNoneBlank(dto.getPassword())){
            //1.1 根据手机号查询用户信息
            ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if(apUser == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户信息不存在");
            }
            //1.2 比对密码
            String salt = apUser.getSalt();
            String password = dto.getPassword();
            String pswd = DigestUtils.md5DigestAsHex((password+salt).getBytes());
            if (!pswd.equals(apUser.getPassword())){
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
            //1.3 返回数据  jwt  user
            String token = AppJwtUtil.getToken(apUser.getId().longValue());
            Map<String, Object> map = new HashMap<>();
            map.put("token",token);
            apUser.setPassword("");
            apUser.setSalt("");
            map.put("user",apUser);
            return ResponseResult.okResult(map);
        }else {
            Map<String,Object> map = new HashMap<>();
            map.put("token",AppJwtUtil.getToken(0L));
            return  ResponseResult.okResult(map);
            //2.游客登录
        }
    }
}
