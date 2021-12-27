package com.egu.boot.BoardGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	Reservation findByBookerNameAndPhoneNumber(String bookername, String phoneNumber);
	
}
