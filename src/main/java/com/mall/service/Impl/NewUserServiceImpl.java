package com.mall.service.Impl;

import com.mall.domains.po.NewUser;
import com.mall.mapper.NewUserMapper;
import com.mall.service.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewUserServiceImpl implements NewUserService {
    @Autowired
    private NewUserMapper newUserMapper;

    @Override
    public String signUp(NewUser newUser) {
        if (newUserMapper.countByUsername(newUser.getUsername()) > 0) {
            return "Username already exists.";
        }
        System.out.println(">> Calling insertUser");
        int rows = newUserMapper.insertUser(newUser);
        System.out.println(">> Inserted rows: " + rows);
        return rows > 0 ? "Signup successful." : "Signup failed.";
    }

}
