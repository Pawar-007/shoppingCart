package com.shoppingcart.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtUser {
	private Long userId;
    private String email;
    private String role;
}
