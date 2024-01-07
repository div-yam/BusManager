package com.busManager.busmanager.repositories;

import com.busManager.busmanager.data.dto.AddUserRequest;
import com.busManager.busmanager.data.dto.User;
import com.busManager.busmanager.data.request.SignInRequest;

public interface AuthRepo {
    public User addUser(AddUserRequest addUserRequest);
    public User getUser(String email);
}
