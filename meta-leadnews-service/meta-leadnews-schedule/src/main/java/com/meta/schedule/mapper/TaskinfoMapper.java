package com.meta.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meta.model.schedule.pojos.Taskinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface TaskinfoMapper extends BaseMapper<Taskinfo> {
    List<Taskinfo> queryFutureTime(@Param("taskType")int type, @Param("priority")int priority, @Param("future") Date future);
}
