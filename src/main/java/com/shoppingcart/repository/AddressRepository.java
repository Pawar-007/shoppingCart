package com.shoppingcart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	 List<Address> findByUser_UserId(Long userId);
}
