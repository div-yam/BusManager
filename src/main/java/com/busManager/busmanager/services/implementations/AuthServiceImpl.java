package com.busManager.busmanager.services.implementations;

import com.busManager.busmanager.data.UserRole;
import com.busManager.busmanager.data.dto.AddUserRequest;
import com.busManager.busmanager.data.dto.User;
import com.busManager.busmanager.data.request.LoginRequest;
import com.busManager.busmanager.data.request.SignInRequest;
import com.busManager.busmanager.repositories.AuthRepo;
import com.busManager.busmanager.services.AuthService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthRepo authRepo;
    @Override
    public User signIn(SignInRequest signInRequest) {
        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setEmail(signInRequest.getEmail().toLowerCase());
        addUserRequest.setName(signInRequest.getName());
        addUserRequest.setPassword(BCrypt.hashpw(signInRequest.getPassword(),BCrypt.gensalt(10)));
        User user= authRepo.addUser(addUserRequest);
        return user;
    }

    @Override
    public User loginIn(LoginRequest loginRequest) {
        User user= authRepo.getUser(loginRequest.getEmail());
        if(user.getRole().equals(UserRole.ADMIN) )
        {
            if(!user.getPassword().equals(loginRequest.getPassword())) {
                return null;
                //todo
                //thow exception
            }
            return user;

        }
        if( !BCrypt.checkpw(loginRequest.getPassword(),user.getPassword())) {
            return null;
            //throw exception}
        }

        return user;
    }
}
