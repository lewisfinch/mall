package com.mall.mapper;

import com.mall.domains.po.NewUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NewUserMapper {
    @Select("SELECT COUNT(*) FROM user WHERE username = #{username}")
    int countByUsername(String username);

    @Insert("INSERT INTO user (username, password, fname, lname, phone, balance) VALUES (#{username}, #{password}, #{fname}, #{lname}, #{phone}, #{balance})")
    int insertUser(NewUser newUser);
}
