package com.mall.domains.po;

import lombok.*;


@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String phone;
    @Setter
    private Integer balance = 1000;
}
