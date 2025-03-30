package com.mall.service.Impl;

import com.mall.mapper.UserMapper;
import com.mall.po.User;
import com.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        String salt = user.getUsername() + "MALL";
        String hashedPassword = hashPassword(user.getPassword(), salt);

        User loginUser = new User();
        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(hashedPassword);
        System.out.println(hashedPassword);

        return userMapper.getByUsernameAndPassword(loginUser);
    }

    @Override
    public String hashPassword(String password, String salt) {
        try {
            String saltedPassword = salt + password;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
