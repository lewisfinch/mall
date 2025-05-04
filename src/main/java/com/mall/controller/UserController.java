package com.mall.controller;

import com.mall.domains.dto.LoginDTO;
import com.mall.domains.dto.SignUpDTO;
import com.mall.domains.vo.LoginVO;
import com.mall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("User login: {}", loginDTO);
            LoginVO loginVO = userService.login(loginDTO);

            response.put("status", "success");
            response.put("message", "Login successful");
            response.put("username", loginVO.getUsername());
            response.put("userId", loginVO.getUserId()); // Make sure LoginVO has this field

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.warn("Login failed: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody SignUpDTO signUpDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("User sign up: {}", signUpDTO);
            userService.signUp(signUpDTO);

            response.put("status", "success");
            response.put("message", "Signup successful");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.warn("Signup failed: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "Signup failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
