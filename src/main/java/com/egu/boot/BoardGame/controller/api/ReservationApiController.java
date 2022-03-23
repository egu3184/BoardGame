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
import com.egu.boot.BoardGame.model.api.SingleResult;
import com.egu.boot.BoardGame.model.dto.ReservationDto;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationRequestDto;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationResponseDto;
import com.egu.boot.BoardGame.repository.ReservationRepository;
import com.egu.boot.BoardGame.service.ReservationService;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

	private final ResponseService responseService;
	private final ReservationService ReservationService;

	// 예약
	@PostMapping("/reservations")
	public SingleResult<ReservationResponseDto> saveReservation(@RequestBody ReservationRequestDto reservationDto) {
		ReservationResponseDto dto = ReservationService.예약등록(reservationDto);
		return responseService.getSingleResult(dto);
	}
	
	
	// id로 예약 조회(추구 권한 넣을 예정)
	@GetMapping("/reservations/{id}")
	public SingleResult<ReservationResponseDto> findReservation(@PathVariable int id) {
		ReservationResponseDto dto = ReservationService.예약조회(id);
		return responseService.getSingleResult(dto);
	}
	
	//예약 번호로 예약 조회
	@GetMapping("/reservations")
	public SingleResult<ReservationResponseDto> getReservation(
			@RequestParam(value="reservationNumber") String reservationNum){
		ReservationResponseDto dto = ReservationService.예약번호조회(reservationNum);
		return responseService.getSingleResult(dto);
	}

	
	
	
	
	// 예약 검색 리스트 조회 (예약자 이름, 전화번호, 혹은 id)
//	@GetMapping("/reservations")
//	public CommonResult findReservation(
//			@RequestParam(value = "bookerName", required = false) String bookerName,
//			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
//			@RequestParam(value="id", required = false) Integer id) {
//		List<Reservation> reservation = ReservationService.예약검색조회(bookerName, phoneNumber,id);
//		return responseService.getListResult(reservation);
//	}

}
