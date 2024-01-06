package com.busManager.busmanager.controllers;

import com.busManager.busmanager.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class Admin {

    @Autowired
    AdminService adminService;
    @PostMapping("/add")
    public String add(){
        adminService.add();
        return null;
    }

    @PutMapping("/update")
    public String update(){
        return null;
    }

    @DeleteMapping("/delete")
    public String delete(){
        return null;
    }

}
