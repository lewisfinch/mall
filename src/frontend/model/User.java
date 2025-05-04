package model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User {
    @Setter
    private int id;
    private String username;
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}