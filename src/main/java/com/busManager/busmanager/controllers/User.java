package com.busManager.busmanager.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class User {

    @GetMapping("/seach")
    public String search(){
        return null;
    }

    @GetMapping("/check-eligibility")
    public String checkEligibility(){
        return null;
    }

    @PutMapping("/hold")
    public String hold(){
        return null;
    }

    @PutMapping("/book")
    public String book(){
        return null;
    }

    @PutMapping("/cancel")
    public String cancel(){
        return null;
    }
}
