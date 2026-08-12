package com.shoppingcart.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcart.DTO.ProductDTO;
import com.shoppingcart.model.Product;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.ProductService;

public class ProductServiceImpl implements ProductService{

	   @Autowired
	    private ProductRepository productRepository;


	    @Override
	    public Product addProduct(Product product) {

	        return productRepository.save(product);
	    }


	    @Override
	    public Product updateProduct(Long productId, Product product) {

	        Product existingProduct = productRepository.findById(productId)
	                .orElseThrow(() ->
	                    new RuntimeException("Product not found with id: " + productId)
	                );

	        existingProduct.setProductName(product.getProductName());
	        existingProduct.setDescription(product.getDescription());
	        existingProduct.setPrice(product.getPrice());
	        existingProduct.setStockQuantity(product.getStockQuantity());
	        existingProduct.setImageUrl(product.getImageUrl());

	        existingProduct.setCategory(product.getCategory());
	        existingProduct.setBrand(product.getBrand());

	        return productRepository.save(existingProduct);
	    }

	    @Override
	    public void deleteProduct(Long productId) {

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() ->
	                    new RuntimeException("Product not found with id: " + productId)
	                );

	        productRepository.delete(product);
	    }


	    @Override
	    public ProductDTO getProduct(Long productId) {

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() ->
	                    new RuntimeException("Product not found with id: " + productId)
	                );

	        return convertToDTO(product);
	    }


	    @Override
	    public List<ProductDTO> getAllProducts() {

	        return productRepository.findAll()
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    @Override
	    public List<ProductDTO> search(String keyword) {

	        return productRepository
	                .findByProductNameContainingIgnoreCase(keyword)
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    @Override
	    public List<ProductDTO> getProductsByCategory(Long categoryId) {

	        return productRepository
	                .findByCategory_CategoryId(categoryId)
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    @Override
	    public List<ProductDTO> getProductsByBrand(Long brandId) {

	        return productRepository
	                .findByBrand_BrandId(brandId)
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    @Override
	    public List<ProductDTO> getProductsByPrice(double min, double max) {

	        BigDecimal minimum = BigDecimal.valueOf(min);
	        BigDecimal maximum = BigDecimal.valueOf(max);

	        return productRepository
	                .findByPriceBetween(minimum, maximum)
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    private ProductDTO convertToDTO(Product product) {

	        ProductDTO dto = new ProductDTO();

	        dto.setProductId(product.getProductId());
	        dto.setName(product.getProductName());
	        dto.setDescription(product.getDescription());
	        dto.setPrice(product.getPrice());
	        dto.setStockQuantity(product.getStockQuantity());
	        dto.setImageUrl(product.getImageUrl());

	        if (product.getCategory() != null) {

	            dto.setCategoryId(
	                product.getCategory().getCategoryId()
	            );

	            dto.setCategoryName(
	                product.getCategory().getCategoryName()
	            );
	        }

	        if (product.getBrand() != null) {

	            dto.setBrandId(
	                product.getBrand().getBrandId()
	            );

	            dto.setBrandName(
	                product.getBrand().getBrandName()
	            );
	        }

	        return dto;
	    }

}
