package com.example.minipropertymanagement.controller;


import com.example.minipropertymanagement.exception.NotFoundException;
import com.example.minipropertymanagement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping(params = {"ownerId", "approve"})
    public void approveOwner(@RequestParam Long ownerId) throws NotFoundException {
        adminService.approveOwner(ownerId);
    }


    @PutMapping(params = {"ownerId", "block"})
    public void blockOwner(@RequestParam Long ownerId) throws NotFoundException {
        adminService.blockOwner(ownerId);
    }
}
