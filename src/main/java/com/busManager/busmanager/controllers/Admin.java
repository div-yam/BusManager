package com.busManager.busmanager.controllers;

import com.busManager.busmanager.data.UserRole;
import com.busManager.busmanager.data.request.AddBusRequest;
import com.busManager.busmanager.data.request.DeleteBusRequest;
import com.busManager.busmanager.data.request.UpdateBusRequest;
import com.busManager.busmanager.services.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class Admin {

    @Autowired
    AdminService adminService;
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody AddBusRequest addBusRequest, HttpServletRequest httpServletRequest){
        UserRole userRole = UserRole.valueOf(String.valueOf(httpServletRequest.getAttribute("role")));
        if(!userRole.equals(UserRole.ADMIN)){
            return new ResponseEntity<>("forbidden",HttpStatus.FORBIDDEN);
        }

        adminService.add(addBusRequest);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/update")
    public String update(@RequestBody UpdateBusRequest updateBusRequest){
        adminService.update(updateBusRequest);
        return null;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody DeleteBusRequest deleteBusRequest, HttpServletRequest httpServletRequest){
        UserRole userRole = UserRole.valueOf(String.valueOf(httpServletRequest.getAttribute("role")));
        if(!userRole.equals(UserRole.ADMIN)){
            return new ResponseEntity<>("forbidden",HttpStatus.FORBIDDEN);
        }
        adminService.delete(deleteBusRequest);
        return null;
    }

}
