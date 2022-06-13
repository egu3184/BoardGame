package com.egu.boot.BoardGame.repository;

import com.egu.boot.BoardGame.model.QReservation;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationRequestDto;

public interface QReservationRepository  {
	
	QReservation reservation = QReservation.reservation;
	
	long updateReservation(ReservationRequestDto requestDto, User user);

}
