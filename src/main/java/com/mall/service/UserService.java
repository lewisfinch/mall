package com.mall.service;

import com.mall.domains.dto.LoginDTO;
import com.mall.domains.po.User;
import com.mall.domains.vo.LoginVO;

public interface UserService {

    LoginVO login(LoginDTO loginDTO);

    String hashPassword(String password, String salt);
}
