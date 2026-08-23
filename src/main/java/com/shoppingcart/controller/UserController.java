package com.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoppingcart.DTO.JwtUser;
import com.shoppingcart.DTO.LoginRequest;
import com.shoppingcart.DTO.LoginResponse;
import com.shoppingcart.DTO.RegisterResponse;
import com.shoppingcart.enumerated.Role;
import com.shoppingcart.model.User;
import com.shoppingcart.service.JwtService;
import com.shoppingcart.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {

            RegisterResponse response = userService.register(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody User user) {

        try {

            if (token == null || token.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Authorization token is missing");
            }

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            JwtUser jwtUser = jwtService.extractUser(token);

            if (jwtUser == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid authorization token");
            }

            if (!Role.ADMIN.name().equals(jwtUser.getRole())) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Access denied. Only administrators can create a new admin.");
            }

            RegisterResponse response =
                    userService.createAdmin(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {
        System.out.println(request.getEmail()+" "+request.getPassword());
        try {

            LoginResponse response =
                    userService.login(request);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @RequestHeader(value = "Authorization", required = false) String token) {

        try {

            Long userId = getUserIdFromToken(token);

            User user = userService.getProfile(userId);

            return ResponseEntity.ok(user);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }



    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody User user) {

        try {

            Long userId = getUserIdFromToken(token);

            User updatedUser =
                    userService.updateProfile(userId, user);

            return ResponseEntity.ok(updatedUser);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/profile/password")
    public ResponseEntity<?> changePassword(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        try {

            Long userId = getUserIdFromToken(token);

            userService.changePassword(
                    userId,
                    oldPassword,
                    newPassword
            );

            return ResponseEntity.ok(
                    "Password changed successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUser(
            @RequestHeader(value = "Authorization", required = false) String token) {

        try {

            Long userId = getUserIdFromToken(token);

            userService.deleteUser(userId);

            return ResponseEntity.ok(
                    "User deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    private Long getUserIdFromToken(String token) {

        if (token == null || token.isBlank()) {
            throw new RuntimeException(
                    "Authorization token is missing"
            );
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        JwtUser jwtUser = jwtService.extractUser(token);

        if (jwtUser == null) {
            throw new RuntimeException(
                    "Invalid authorization token"
            );
        }

        return jwtUser.getUserId();
    }
}