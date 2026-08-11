package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
