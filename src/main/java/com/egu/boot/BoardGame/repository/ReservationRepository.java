package com.egu.boot.BoardGame.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.Reservation;
import com.egu.boot.BoardGame.model.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

//	Reservation findByBookerNameAndPhoneNumber(String bookername, String phoneNumber);
	Optional<Reservation> findByReservationNumber(String reservationNumber);
	
	Page<Reservation> findByUserOrderByIdDesc(User user, Pageable pageable);
	
	Optional<Reservation> findByReservationNumber(Integer reservationNumber);
	
	Optional<Reservation> findByUserAndId(User user, Integer id);
	
	
}
