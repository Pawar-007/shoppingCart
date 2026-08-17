package com.shoppingcart.model;


import com.shoppingcart.enumerated.AddressType;
import jakarta.persistence.*;
import lombok.*;
import com.shoppingcart.model.User;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100)
    private String fullName;

    @Column(length = 15)
    private String phone;

    @Column(length = 255)
    private String addressLine1;

    @Column(length = 255)
    private String addressLine2;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 100)
    private String country;

    @Column(length = 10)
    private String pincode;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    private Boolean isDefault=false;
}