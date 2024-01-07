package com.busManager.busmanager.controllers;

import com.busManager.busmanager.Constant;
import com.busManager.busmanager.data.dto.User;
import com.busManager.busmanager.data.request.LoginRequest;
import com.busManager.busmanager.data.request.SignInRequest;
import com.busManager.busmanager.exceptions.PasswordMismatchException;
import com.busManager.busmanager.exceptions.SignUpException;
import com.busManager.busmanager.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Auth {
    @Autowired
    AuthService authService;
    @GetMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest request){
        try {
            User user = authService.loginIn(request);
            return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
        } catch(PasswordMismatchException e) {
            return ResponseEntity.status(e.getStatus()).body(Collections.singletonMap("error", e.getMessage()));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Internal Server Error"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody SignInRequest request){
        try {
            User user= authService.signIn(request);
            return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
        } catch(SignUpException e) {
            return ResponseEntity.status(e.getStatus()).body(Collections.singletonMap("error", e.getMessage()));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Internal Server Error"));
        }
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
