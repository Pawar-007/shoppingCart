package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
