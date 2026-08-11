package com.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoppingcart.DTO.JwtUser;
import com.shoppingcart.DTO.ProductDTO;
import com.shoppingcart.enumerated.Role;
import com.shoppingcart.model.Product;
import com.shoppingcart.service.JwtService;
import com.shoppingcart.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private JwtService jwtService;

    // Add Product
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestHeader(value = "Authorization", required = false) String token,@RequestBody Product product) {
    	if(token == null || token.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization token is missing");
        }
    	
    	if (!token.startsWith("Bearer ")) {
    	    return ResponseEntity
    	            .status(HttpStatus.UNAUTHORIZED)
    	            .body("Invalid Authorization header");
    	}

    	token = token.substring(7);
    	
    	JwtUser jwtuser=jwtService.extractUser(token);
    	if (!Role.ADMIN.name().equals(jwtuser.getRole())) {
    		return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body("Access denied. Only administrators can create a new admin.");

    	}
        return ResponseEntity.ok(productService.addProduct(product));
    }

    // Update Product
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product product) {

        return ResponseEntity.ok(
                productService.updateProduct(productId, product)
        );
    }

    // Delete Product
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId) {

        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    // Get Product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                productService.getProduct(productId)
        );
    }

    // Get All Products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }

    // Search Products
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> search(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                productService.search(keyword)
        );
    }

    // Products by Category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(categoryId)
        );
    }

    // Products by Brand
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<ProductDTO>> getProductsByBrand(
            @PathVariable Long brandId) {

        return ResponseEntity.ok(
                productService.getProductsByBrand(brandId)
        );
    }

    // Products by Price Range
    @GetMapping("/price")
    public ResponseEntity<List<ProductDTO>> getProductsByPrice(
            @RequestParam double min,
            @RequestParam double max) {

        return ResponseEntity.ok(
                productService.getProductsByPrice(min, max)
        );
    }
}