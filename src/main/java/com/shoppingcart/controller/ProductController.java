package com.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoppingcart.DTO.JwtUser;
import com.shoppingcart.DTO.ProductDTO;
import com.shoppingcart.DTO.ProductRequestDTO;
import com.shoppingcart.enumerated.Role;
import com.shoppingcart.model.Product;
import com.shoppingcart.service.AuthService;
import com.shoppingcart.service.JwtService;
import com.shoppingcart.service.ProductService;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AuthService authService;

    // Add Product
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody ProductRequestDTO productRequest) {
        System.out.println("request controller "+productRequest);
        if (!authService.isAdmin(token)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Access denied. Only administrators are allowed.");
        }

        return ResponseEntity.ok(
                productService.addProduct(productRequest)
        );
    }

    //update
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Long productId,
            @RequestBody Product product) {
    	if (!authService.isAdmin(token)) {
    	    return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body("Access denied. Only administrators are allowed.");
    	}

        return ResponseEntity.ok(
                productService.updateProduct(productId, product)
        );
    }

    // Delete Product
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Long productId) {

    	if (!authService.isAdmin(token)) {
    	    return ResponseEntity
    	            .status(HttpStatus.FORBIDDEN)
    	            .body("Access denied. Only administrators are allowed.");
    	}

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