package com.egu.boot.BoardGame.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

	// 회원, 비회원 예약
	@PostMapping("/reservations")
	public CommonResult saveReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
		Reservation reservation = ReservationService.예약등록(reservationRequestDto);
		return responseService.getSingleResult(reservation);
	}

	// id로 예약 조회
	@GetMapping("/reservations/{id}")
	public CommonResult findReservation(@PathVariable int id) {
		Reservation reservation = ReservationService.예약조회(id);
		return responseService.getSingleResult(reservation);
	}

	// 예약 검색 리스트 조회
	@GetMapping("/reservations")
	public CommonResult findReservation(
			@RequestParam(value = "bookerName", required = false) String bookerName,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value="id", required = false) Integer id) {
		List<Reservation> reservation = ReservationService.예약검색조회(bookerName, phoneNumber,id);
		return responseService.getListResult(reservation);
	}

}
