package com.shoppingcart.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String firstName;
    private String email;
    private String role;
    private String token;
}
