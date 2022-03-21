package com.egu.boot.BoardGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
