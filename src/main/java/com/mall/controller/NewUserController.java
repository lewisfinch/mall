package com.mall.controller;

import com.mall.domains.dto.SignUpDTO;
import com.mall.domains.po.NewUser;
import com.mall.service.NewUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NewUserController {
    @Autowired
    private NewUserService newUserService;

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpDTO dto) {
        NewUser newUser = new NewUser();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword());
        newUser.setFname(dto.getFname());
        newUser.setLname(dto.getLname());
        newUser.setPhone(dto.getPhone());
        return newUserService.signUp(newUser);
    }
}
