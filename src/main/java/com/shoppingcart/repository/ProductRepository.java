package com.shoppingcart.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	    List<Product> findByProductNameContainingIgnoreCase(String keyword);

	    List<Product> findByCategory_CategoryId(Long categoryId);

	    List<Product> findByBrand_BrandId(Long brandId);

	    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
}
