package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
