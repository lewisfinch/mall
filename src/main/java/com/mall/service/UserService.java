package com.mall.service;

import com.mall.po.User;

public interface UserService {

    User login(User user);

    String hashPassword(String password, String salt);
}
