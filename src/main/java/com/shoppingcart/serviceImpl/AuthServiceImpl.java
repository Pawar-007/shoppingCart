package com.shoppingcart.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.DTO.JwtUser;
import com.shoppingcart.enumerated.Role;
import com.shoppingcart.service.AuthService;
import com.shoppingcart.service.JwtService;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean isAdmin(String token) {

        if (token == null || token.isBlank()) {
            return false;
        }

        if (!token.startsWith("Bearer ")) {
            return false;
        }

        token = token.substring(7);

        JwtUser jwtUser = jwtService.extractUser(token);

        return Role.ADMIN.name().equals(jwtUser.getRole());
    }

}
