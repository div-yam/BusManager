package com.busManager.busmanager.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String role;
}