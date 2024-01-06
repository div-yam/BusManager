package com.busManager.busmanager.controllers;

import com.busManager.busmanager.data.request.SearchRequest;
import com.busManager.busmanager.data.response.SearchResponse;
import com.busManager.busmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class User {
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest searchRequest) {
        return new ResponseEntity<>(userService.search(searchRequest), HttpStatus.OK);

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
