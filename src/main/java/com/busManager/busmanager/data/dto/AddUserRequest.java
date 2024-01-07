package com.busManager.busmanager.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    String name;
    String email;
    String password;
}
