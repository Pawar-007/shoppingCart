package com.shoppingcart.service;

import java.util.List;

import com.shoppingcart.DTO.ProductDTO;
import com.shoppingcart.model.Product;

public interface ProductService {

    Product addProduct(Product product);

    Product updateProduct(Long productId,
                          Product product);

    void deleteProduct(Long productId);

    ProductDTO getProduct(Long productId);

    List<ProductDTO> getAllProducts();

    List<ProductDTO> search(String keyword);

    List<ProductDTO> getProductsByCategory(Long categoryId);

    List<ProductDTO> getProductsByBrand(Long brandId);

    List<ProductDTO> getProductsByPrice(double min,
                                        double max);

}