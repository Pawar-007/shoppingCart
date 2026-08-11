//package com.shoppingcart.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shoppingcart.DTO.LoginRequest;
//import com.shoppingcart.DTO.LoginResponse;
//import com.shoppingcart.model.User;
//import com.shoppingcart.service.UserService;
//
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin("*")
//public class AuthController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<User> register(@RequestBody User user) {
//
//        return ResponseEntity.ok(userService.register(user));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
//
//        return ResponseEntity.ok(userService.login(request));
//    }
//}