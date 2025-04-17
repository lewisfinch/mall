package com.mall.service.Impl;

import com.mall.domains.dto.LoginDTO;
import com.mall.domains.vo.LoginVO;
import com.mall.mapper.UserMapper;
import com.mall.domains.po.User;
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
    public LoginVO login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        if(username == null || password == null){
            return null;
        }

        String salt = username + "MALL";
        String hashedPassword = hashPassword(password, salt);
        System.out.println(hashedPassword);

        User user = userMapper.getByUsername(loginDTO.getUsername());
        if(!hashedPassword.equals(user.getPassword())){
            return null;
        }
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setBalance(user.getBalance());
        return loginVO;
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
