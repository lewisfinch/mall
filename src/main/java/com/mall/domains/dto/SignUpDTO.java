package com.mall.domains.dto;

import lombok.Data;

@Data
public class SignUpDTO {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String email;
}
