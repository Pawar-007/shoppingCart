package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
