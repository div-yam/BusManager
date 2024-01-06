package com.busManager.busmanager.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Auth {


    @GetMapping("/login")
    public String login(@RequestBody Map<String,String> request){
        return null;
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody Map<String, String> request){
        return null;
    }

}
