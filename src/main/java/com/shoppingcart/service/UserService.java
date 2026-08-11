package com.shoppingcart.service;

import com.shoppingcart.DTO.LoginRequest;
import com.shoppingcart.DTO.LoginResponse;
import com.shoppingcart.DTO.RegisterResponse;
import com.shoppingcart.model.User;

public interface UserService {

	RegisterResponse  register(User user);

    LoginResponse login(LoginRequest request);

    User getProfile(Long userId);

    User updateProfile(Long userId, User user);

    void changePassword(Long userId,
                        String oldPassword,
                        String newPassword);

    void deleteUser(Long userId);
    public RegisterResponse createAdmin(User user);
}