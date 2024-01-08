package com.busManager.busmanager.data.dto;

import com.busManager.busmanager.data.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}