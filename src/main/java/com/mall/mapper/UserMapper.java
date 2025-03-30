package com.mall.mapper;

import com.mall.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = #{username} and password = #{password}")
    User getByUsernameAndPassword(User user);
}
