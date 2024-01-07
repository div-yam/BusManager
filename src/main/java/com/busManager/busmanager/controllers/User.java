package com.busManager.busmanager.controllers;

import com.busManager.busmanager.data.request.*;
import com.busManager.busmanager.exceptions.BookException;
import com.busManager.busmanager.exceptions.CheckEligibilityException;
import com.busManager.busmanager.exceptions.HoldBookingException;
import com.busManager.busmanager.exceptions.SearchBusException;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/user")
public class User {

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest searchRequest) {
        try {
            return new ResponseEntity<>(userService.search(searchRequest), HttpStatus.OK);
        } catch (SearchBusException e) {
            return new ResponseEntity<>(e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-eligibility")
    public ResponseEntity<CheckEligibilityResponse> checkEligibility(
            @RequestBody CheckEligibilityRequest checkEligibilityRequest) {
        try {
            return new ResponseEntity<>(userService.checkEligibility(checkEligibilityRequest), HttpStatus.OK);
        } catch (CheckEligibilityException e) {
            return new ResponseEntity<>(e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/hold")
    public ResponseEntity<HoldResponse> hold(@RequestBody HoldRequest holdRequest, HttpServletRequest httpServletRequest) {
        try {
            Integer userId = Integer.valueOf(String.valueOf(httpServletRequest.getAttribute("userId")));
            holdRequest.setUserId(userId);
            return new ResponseEntity<>(userService.hold(holdRequest), HttpStatus.OK);
        } catch (CheckEligibilityException e) {
            return new ResponseEntity<>(e.getStatus());
        } catch (HoldBookingException e) {
            return new ResponseEntity<>(e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/book")
    public ResponseEntity<BookResponse> book(@RequestBody BookRequest bookRequest, HttpServletRequest httpServletRequest) {
        try {
            Integer userId = Integer.valueOf(String.valueOf(httpServletRequest.getAttribute("userId")));
            bookRequest.setUserId(userId);
            return new ResponseEntity<>(userService.book(bookRequest), HttpStatus.OK);
        } catch (BookException e) {
            return new ResponseEntity<>(e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/cancel")
    public ResponseEntity<CancelResponse> cancel(@RequestBody CancelRequest cancelRequest, HttpServletRequest httpServletRequest) {
        try {
            Integer userId = Integer.valueOf(String.valueOf(httpServletRequest.getAttribute("userId")));
            cancelRequest.setUserId(userId);
            return new ResponseEntity<>(userService.cancel(cancelRequest), HttpStatus.OK);
        } catch (BookException e) {
            return new ResponseEntity<>(e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
