package com.busManager.busmanager.controllers;

import com.busManager.busmanager.data.request.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busManager.busmanager.data.response.*;
import com.busManager.busmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.busManager.busmanager.data.response.SearchResponse;

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
    public ResponseEntity<CheckEligibilityResponse> checkEligibility(
            @RequestBody CheckEligibilityRequest checkEligibilityRequest){
        return new ResponseEntity<>(userService.checkEligibility(checkEligibilityRequest), HttpStatus.OK);
    }

    @PutMapping("/hold")
    public ResponseEntity<HoldResponse> hold(@RequestBody HoldRequest holdRequest){
        return new ResponseEntity<>(userService.hold(holdRequest), HttpStatus.OK);
    }

    @PutMapping("/book")
    public ResponseEntity<BookResponse> book(@RequestBody BookRequest bookRequest){
        return new ResponseEntity<>(userService.book(bookRequest), HttpStatus.OK);
    }

    @PutMapping("/cancel")
    public ResponseEntity<CancelResponse> cancel(@RequestBody CancelRequest cancelRequest){
        return new ResponseEntity<>(userService.cancel(cancelRequest), HttpStatus.OK);
    }
}
