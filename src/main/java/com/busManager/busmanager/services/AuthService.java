package com.busManager.busmanager.services;

import com.busManager.busmanager.data.dto.User;
import com.busManager.busmanager.data.request.LoginRequest;
import com.busManager.busmanager.data.request.SignInRequest;

public interface AuthService {
    public User signIn(SignInRequest signInRequest);
    public User loginIn(LoginRequest loginRequest);
}
