package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long>{

}
