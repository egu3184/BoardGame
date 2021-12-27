package com.egu.boot.BoardGame.repository;

import java.util.List;

import com.egu.boot.BoardGame.model.QReservation;
import com.egu.boot.BoardGame.model.Reservation;

public interface FindReservationRepository {

	QReservation reservation = QReservation.reservation;
	
	List<Reservation> searchReservation(String bookName, String phoneNumber, Integer id);

}
