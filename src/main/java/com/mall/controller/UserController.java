package com.mall.controller;

import com.mall.domains.dto.LoginDTO;
import com.mall.domains.po.Result;
import com.mall.domains.po.User;
import com.mall.domains.vo.LoginVO;
import com.mall.service.UserService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginVO login(@RequestBody LoginDTO loginDTO) {
        log.info("User login: {}", loginDTO);
        return userService.login(loginDTO);
    }
}
