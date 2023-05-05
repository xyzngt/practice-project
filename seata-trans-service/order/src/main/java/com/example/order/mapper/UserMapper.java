package com.example.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.order.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 获取所有的userId,用于初始化布隆过滤器
     * @return List<Long>
     */
    @Select("select id from user")
    List<Long> selectAllUserId();
}
