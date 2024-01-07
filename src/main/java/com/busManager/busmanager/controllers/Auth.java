package com.busManager.busmanager.controllers;

import com.busManager.busmanager.Constant;
import com.busManager.busmanager.data.dto.User;
import com.busManager.busmanager.data.request.LoginRequest;
import com.busManager.busmanager.data.request.SignInRequest;
import com.busManager.busmanager.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Auth {

    @Autowired
    AuthService authService;
    @GetMapping("/login")
    public Map<String,String> login(@RequestBody LoginRequest request){
        User user= authService.loginIn(request);
        return generateJWTToken(user);
    }

    @PostMapping("/signin")
    public Map<String, String> signIn(@RequestBody SignInRequest request){

        User user= authService.signIn(request);
        return generateJWTToken(user);
    }

    private Map<String, String> generateJWTToken(User user){
        long timeStamp= System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constant.API_SECRET_KEY)
                .setIssuedAt(new Date(timeStamp))
                .setExpiration(new Date(timeStamp+Constant.TOKEN_VALIDITY))
                .claim("userId",user.getUserId())
                .claim("name",user.getName())
                .claim("role",user.getRole())
                .compact();
        Map<String,String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

}
