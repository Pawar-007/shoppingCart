package com.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.DTO.OrderDTO;
import com.shoppingcart.model.User;
import com.shoppingcart.service.AdminService;
import com.shoppingcart.service.AuthService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;


    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can view users");
        }

        try {
            return ResponseEntity.ok(adminService.getAllUsers());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch users");
        }
    }


    @GetMapping("/orders")
    public ResponseEntity<?> getOrders(
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can view orders");
        }

        try {
            return ResponseEntity.ok(adminService.getAllOrders());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch orders");
        }
    }


    @PutMapping("/status/{orderId}")
    public ResponseEntity<?> updateStatus(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Long orderId,
            @RequestParam String status) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can update order status");
        }

        try {
            adminService.updateStatus(orderId, status);

            return ResponseEntity.ok("Order status updated successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update order status");
        }
    }


    @GetMapping("/total-users")
    public ResponseEntity<?> totalUsers(
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can view total users");
        }

        try {
            return ResponseEntity.ok(adminService.totalUsers());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch total users");
        }
    }


    @GetMapping("/total-orders")
    public ResponseEntity<?> totalOrders(
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can view total orders");
        }

        try {
            return ResponseEntity.ok(adminService.totalOrders());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch total orders");
        }
    }


    @GetMapping("/total-products")
    public ResponseEntity<?> totalProducts(
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can view total products");
        }

        try {
            return ResponseEntity.ok(adminService.totalProducts());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch total products");
        }
    }
}