package com.busManager.busmanager.controllers;

<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import com.busManager.busmanager.data.request.*;
import com.busManager.busmanager.data.response.*;
import com.busManager.busmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
>>>>>>> Stashed changes

@RestController
@RequestMapping("/user")
public class User {
<<<<<<< Updated upstream

    @GetMapping("/seach")
    public String search(){
        return null;
=======
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest searchRequest) {
        return new ResponseEntity<>(userService.search(searchRequest), HttpStatus.OK);
>>>>>>> Stashed changes
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
