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
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtService jwtService;


    // =========================
    // REGISTER
    // =========================

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
    	  System.out.println("EMAIL FROM REQUEST = " + user.getEmail());


    	try {
            RegisterResponse response = userService.register(user);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestHeader(value = "Authorization", required = false) String token,@RequestBody User user){
        
    	if(token == null || token.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization token is missing");
        }
    	
    	token = token.replace("Bearer ", "");
    	
    	JwtUser jwtuser=jwtService.extractUser(token);
    	if(!jwtuser.getRole().equals(Role.ADMIN.name())) {
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body("Access denied. Only administrators can create a new admin.");

    	}
    	
    	try {
            RegisterResponse response = userService.createAdmin(user);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        try{
        	LoginResponse response = userService.login(request);

            return ResponseEntity.ok(response);
        }catch (Exception ex) {
                return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());

		}
    }


    // =========================
    // GET PROFILE
    // =========================

    @GetMapping("/{userId}")
    public ResponseEntity<User> getProfile(
            @PathVariable Long userId) {

        User user = userService.getProfile(userId);

        return ResponseEntity.ok(user);
    }


    // =========================
    // UPDATE PROFILE
    // =========================

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateProfile(
            @PathVariable Long userId,
            @RequestBody User user) {

        User updatedUser =
                userService.updateProfile(userId, user);

        return ResponseEntity.ok(updatedUser);
    }

    
    // =========================
    // CHANGE PASSWORD
    // =========================

    @PutMapping("/{userId}/password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        userService.changePassword(
                userId,
                oldPassword,
                newPassword
        );

        return ResponseEntity.ok(
                "Password changed successfully"
        );
    }


    // =========================
    // DELETE USER
    // =========================

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }
}