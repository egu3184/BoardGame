package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Reservation;
import com.egu.boot.BoardGame.model.ReservationRequestDto;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.repository.ReservationRepository;
import com.egu.boot.BoardGame.service.reservationService;
import com.egu.boot.BoardGame.service.api.ResponseService;

@RestController
public class ReservationApiController {

	@Autowired
	ResponseService responseService;
	
	@Autowired
	reservationService reservationService;
	
	//회원, 비회원 예약
	@PostMapping("/reservation")
	public CommonResult saveReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
		Reservation reservation =  reservationService.예약등록(reservationRequestDto);
		return responseService.getSingleResult(reservation);
	}
	
	//예약 조회
	
	
	
	
}
