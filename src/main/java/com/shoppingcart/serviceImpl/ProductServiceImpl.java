package com.shoppingcart.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.DTO.BrandResponseDTO;
import com.shoppingcart.DTO.CategoryResponseDTO;
import com.shoppingcart.DTO.ProductDTO;
import com.shoppingcart.DTO.ProductRequestDTO;
import com.shoppingcart.DTO.ProductResponseDTO;
import com.shoppingcart.model.Brand;
import com.shoppingcart.model.Category;
import com.shoppingcart.model.Product;
import com.shoppingcart.model.ProductImage;
import com.shoppingcart.repository.BrandRepository;
import com.shoppingcart.repository.CategoryRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	   @Autowired
	    private ProductRepository productRepository;
      
	   @Autowired
	   private CategoryRepository categoryRepository;

	   @Autowired
	   private BrandRepository brandRepository;

	   @Override
	   public Product addProduct(ProductRequestDTO request) {
		   System.out.println("request"+request);
		
	       Category category = categoryRepository
	               .findById(request.getCategoryId())
	               .orElseThrow(() ->
	                       new RuntimeException("Category not found")
	               );

	       Brand brand = brandRepository
	               .findById(request.getBrandId())
	               .orElseThrow(() ->
	                       new RuntimeException("Brand not found")
	               );

	       Product product = new Product();

	       product.setProductName(request.getName());
	       product.setDescription(request.getDescription());
	       product.setPrice(request.getPrice());
	       product.setStockQuantity(request.getStockQuantity());

	       product.setCategory(category);
	       product.setBrand(brand);
	       
	       List<ProductImage> images = new ArrayList<>();

	       if (request.getImageUrls() != null) {

	           for (int i = 0; i < request.getImageUrls().size(); i++) {

	               ProductImage image = new ProductImage();

	               image.setImageUrl(request.getImageUrls().get(i));

	               // First image = primary image
	               image.setIsPrimary(i == 0);

	               image.setProduct(product);

	               images.add(image);
	           }
	       }

	       product.setProductImages(images);

	       return productRepository.save(product);
	   }


	    @Override
	    public Product updateProduct(Long productId, ProductRequestDTO product) {

	        Product existingProduct = productRepository.findById(productId)
	                .orElseThrow(() ->
	                    new RuntimeException("Product not found with id: " + productId)
	                );

	        existingProduct.setProductName(product.getName());
	        existingProduct.setDescription(product.getDescription());
	        existingProduct.setPrice(product.getPrice());
	        existingProduct.setStockQuantity(product.getStockQuantity());
	        
	        Category category = categoryRepository.findById(product.getCategoryId())
	                .orElseThrow(() -> new RuntimeException("Category not found"));

	        Brand brand = brandRepository.findById(product.getBrandId())
	                .orElseThrow(() -> new RuntimeException("Brand not found"));

	        existingProduct.setCategory(category);
	        existingProduct.setBrand(brand);

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
	    public ProductResponseDTO getProduct(Long productId) {

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() ->
	                    new RuntimeException("Product not found with id: " + productId)
	                );

	        return convertToDTO(product);
	    }


	    @Override
	    public List<ProductResponseDTO> getAllProducts() {

	        return productRepository.findAll()
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    @Override
	    public List<ProductResponseDTO> search(String keyword) {

	        return productRepository
	                .findByProductNameContainingIgnoreCase(keyword)
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    @Override
	    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {

	        return productRepository
	                .findByCategory_CategoryId(categoryId)
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    @Override
	    public List<ProductResponseDTO> getProductsByBrand(Long brandId) {

	        return productRepository
	                .findByBrand_BrandId(brandId)
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    @Override
	    public List<ProductResponseDTO> getProductsByPrice(double min, double max) {

	        BigDecimal minimum = BigDecimal.valueOf(min);
	        BigDecimal maximum = BigDecimal.valueOf(max);

	        return productRepository
	                .findByPriceBetween(minimum, maximum)
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }


	    private ProductResponseDTO convertToDTO(Product product) {

		    ProductResponseDTO dto = new ProductResponseDTO();
		
		    dto.setProductId(product.getProductId());
		    dto.setName(product.getProductName());
		    dto.setDescription(product.getDescription());
		    dto.setPrice(product.getPrice());
		    dto.setStockQuantity(product.getStockQuantity());
		    dto.setCreatedAt(product.getCreatedAt());
		
		    // Category
		    if (product.getCategory() != null) {
		
		        CategoryResponseDTO categoryDTO = new CategoryResponseDTO();
		
		        categoryDTO.setCategoryId(
		                product.getCategory().getCategoryId()
		        );
		
		        categoryDTO.setCategoryName(
		                product.getCategory().getCategoryName()
		        );
		
		        dto.setCategory(categoryDTO);
		    }
		
		    // Brand
		    if (product.getBrand() != null) {
		
		        BrandResponseDTO brandDTO = new BrandResponseDTO();
		
		        brandDTO.setBrandId(
		                product.getBrand().getBrandId()
		        );
		
		        brandDTO.setBrandName(
		                product.getBrand().getBrandName()
		        );
		
		        dto.setBrand(brandDTO);
		    }
		
		    // Images
		    if (product.getProductImages() != null) {
		
		        List<String> imageUrls = product.getProductImages()
		                .stream()
		                .map(ProductImage::getImageUrl)
		                .collect(Collectors.toList());
		
		        dto.setImageUrl(imageUrls);
		    }
		
		    return dto;
		}
}
