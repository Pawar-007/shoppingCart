package com.shoppingcart.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcart.DTO.ProductDTO;
import com.shoppingcart.model.Product;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.ProductService;

public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Product addProduct(Product product) {
		productRepository.save(product);
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Long productId, Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(Long productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProductDTO getProduct(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDTO> search(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDTO> getProductsByCategory(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDTO> getProductsByBrand(Long brandId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDTO> getProductsByPrice(double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

}
