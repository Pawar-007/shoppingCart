package com.shoppingcart.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.DTO.LoginRequest;
import com.shoppingcart.DTO.LoginResponse;
import com.shoppingcart.DTO.RegisterResponse;
import com.shoppingcart.enumerated.Role;
import com.shoppingcart.model.User;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.service.JwtService;
import com.shoppingcart.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    JwtService jwtService;
	@Override
	public RegisterResponse register(User user) {
		 System.out.println("EMAIL IN SERVICE = " + user.getEmail());
		if (userRepository.existsByEmail(user.getEmail())) {
	        throw new RuntimeException("Email already registered");
	    }
		
		if(user.getRole()==null) {
			user.setRole(Role.CUSTOMER);
		}
	
		User savedUser = userRepository.save(user);

	    return new RegisterResponse(
	            savedUser.getUserId(),
	            savedUser.getFirstName(),
	            savedUser.getLastName(),
	            savedUser.getEmail(),
	            savedUser.getPhone(),
	            savedUser.getRole() != null ? savedUser.getRole().name() : null
	    );
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		Optional<User> result=userRepository.findByEmail(request.getEmail());
		if (result.isEmpty()) {
		    throw new RuntimeException("User not found");
		}
		
		User user = result.get();
		System.out.println(user.getPassword()+" "+request.getPassword());
		if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
		
		String token = jwtService.generateToken(user);

        return new LoginResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getEmail(),
                user.getRole().name(),
                token
        );
	}

	@Override
	public User getProfile(Long userId) {
		 return userRepository.findById(userId)
		            .orElseThrow(() ->
		                new RuntimeException("User not found")
		            );
	}

	@Override
	public User updateProfile(Long userId, User user) {

	    User existingUser = userRepository.findById(userId)
	            .orElseThrow(() ->
	                new RuntimeException("User not found")
	            );

	    existingUser.setFirstName(user.getFirstName());
	    existingUser.setEmail(user.getEmail());
	    existingUser.setPhone(user.getPhone());

	    return userRepository.save(existingUser);
	}

	@Override
	public void changePassword(
	        Long userId,
	        String oldPassword,
	        String newPassword) {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() ->
	                new RuntimeException("User not found")
	            );

	    if (!user.getPassword().equals(oldPassword)) {
	        throw new RuntimeException("Old password is incorrect");
	    }

	    user.setPassword(newPassword);

	    userRepository.save(user);
	}

	@Override
	public void deleteUser(Long userId) {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() ->
	                new RuntimeException("User not found")
	            );

	    userRepository.delete(user);
	}
	
	@Override
	public User createAdmin(User user) {

	    if (userRepository.existsByEmail(user.getEmail())) {
	        throw new RuntimeException("Email already registered");
	    }

	    user.setRole(Role.ADMIN);

	    return userRepository.save(user);
	}
	
}
