package com.example.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.account.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    @Select("select id from user")
    List<Long> selectAllUserId();
}
