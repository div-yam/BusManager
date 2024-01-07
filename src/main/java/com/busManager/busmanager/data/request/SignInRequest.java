package com.busManager.busmanager.data.request;

import com.busManager.busmanager.data.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    String name;
    String email;
    String password;
}
