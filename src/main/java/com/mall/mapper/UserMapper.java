package com.mall.mapper;

import com.mall.domains.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User getByUsername(String username);

    @Select("SELECT COUNT(*) FROM user WHERE username = #{username}")
    int countByUsername(String username);

    @Insert("INSERT INTO user (username, password, fname, lname, email, balance) VALUES (#{username}, #{password}, #{fname}, #{lname}, #{email}, #{balance})")
    void insertUser(User user);

    @Update("UPDATE user SET balance = balance - #{balance} WHERE id = #{userId}")
    void deductBalance(Integer userId, Integer balance);
}
