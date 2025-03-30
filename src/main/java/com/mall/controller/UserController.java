package com.mall.controller;

import com.mall.po.Result;
import com.mall.po.User;
import com.mall.service.UserService;
import com.mall.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("User login: {}", user);
        User u = userService.login(user);

        if(u != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userName", u.getUsername());
            claims.put("balance", u.getBalance());
            String jwt = JwtUtils.generateJwt(claims);
            log.info("jwt: {}", jwt);
            return Result.success(jwt);
        }

        return Result.error("Invalid username or password");
    }
}
