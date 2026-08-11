package com.shoppingcart.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    
    @Column(length = 500)
    private String imageUrl;

    private Boolean isPrimary; // True for main thumbnail, False for other photos

    // Multiple images ek product se belong karti hain
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}