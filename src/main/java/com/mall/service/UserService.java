package com.mall.service;

import com.mall.domains.dto.LoginDTO;
import com.mall.domains.dto.SignUpDTO;
import com.mall.domains.po.User;
import com.mall.domains.vo.LoginVO;

public interface UserService {

    LoginVO login(LoginDTO loginDTO);

    void signUp(SignUpDTO signUpDTO);

    String hashPassword(String password, String salt);

    void deductBalance(Integer userId, Integer balance);

    void addBalance(Integer userId, Integer balance);
}
