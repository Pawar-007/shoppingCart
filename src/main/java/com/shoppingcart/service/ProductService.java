package com.shoppingcart.service;

import java.util.List;

import com.shoppingcart.DTO.ProductDTO;
import com.shoppingcart.DTO.ProductRequestDTO;
import com.shoppingcart.DTO.ProductResponseDTO;
import com.shoppingcart.model.Product;

public interface ProductService {

    Product addProduct(ProductRequestDTO productRequest);

    Product updateProduct(Long productId,
    		ProductRequestDTO product);

    void deleteProduct(Long productId);

    ProductResponseDTO getProduct(Long productId);

    List<ProductResponseDTO> getAllProducts();

    List<ProductResponseDTO> search(String keyword);

    List<ProductResponseDTO> getProductsByCategory(Long categoryId);

    List<ProductResponseDTO> getProductsByBrand(Long brandId);

    List<ProductResponseDTO> getProductsByPrice(double min,
                                        double max);

}