package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Reservation;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.dto.ReservationRequestDto;
import com.egu.boot.BoardGame.repository.ReservationRepository;
import com.egu.boot.BoardGame.service.ReservationService;
import com.egu.boot.BoardGame.service.api.ResponseService;

@RestController
public class ReservationApiController {

	@Autowired
	ResponseService responseService;
	
	@Autowired
	ReservationService ReservationService;
	
	//회원, 비회원 예약
	@PostMapping("/reservation")
	public CommonResult saveReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
		Reservation reservation =  ReservationService.예약등록(reservationRequestDto);
		return responseService.getSingleResult(reservation);
	}
	
	//예약 조회
	@GetMapping("/reservation/{id}")
	public CommonResult findReservation(@RequestBody ReservationRequestDto reservationRequestDto ) {
		Reservation reservation =  ReservationService.예약조회(reservationRequestDto);
		return responseService.getSingleResult(reservation);
	}
	
	
	
}
