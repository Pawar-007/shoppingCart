package com.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.DTO.OrderDTO;
import com.shoppingcart.model.User;
import com.shoppingcart.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<User> getUsers() {

        return adminService.getAllUsers();
    }

    @GetMapping("/orders")
    public List<OrderDTO> getOrders() {

        return adminService.getAllOrders();
    }

    @PutMapping("/status/{orderId}")
    public String updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        adminService.updateStatus(orderId, status);

        return "Status Updated";
    }
}