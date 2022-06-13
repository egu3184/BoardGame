package com.egu.boot.BoardGame.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.Reservation;
import com.egu.boot.BoardGame.model.User;
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

	// 예약 등록
	@PostMapping("/reservations")
	public SingleResult<ReservationResponseDto> saveReservation(@RequestBody ReservationRequestDto reservationDto) {
		ReservationResponseDto dto = ReservationService.예약등록(reservationDto);
		return responseService.getSingleResult(dto);
	}
	
	//비회원 예약 조회
	@GetMapping("/reservations/")
	public SingleResult<ReservationResponseDto> getReservation(@RequestParam Integer reservationNumber ) {
		ReservationResponseDto dto = ReservationService.비회원예약조회(reservationNumber);
		return responseService.getSingleResult(dto);
	}
	
	//회원 예약 조회
	@GetMapping("/reservations")
	public SingleResult<Map<String, Object>> getReservation(Pageable pageable){
		Map<String, Object> map = ReservationService.회원예약조회(pageable);
		return responseService.getSingleResult(map);
	}
	
	//회원 예약 수정
	@PutMapping("/reservations")
	public CommonResult updateUserReservation(@RequestBody ReservationRequestDto requestDto) {
		long result = ReservationService.예약수정(requestDto);
		return result > 0 ? responseService.getSuccessResult() : responseService.getFailResult(ErrorCode.RESERVATION_UPDATE_FAIL);
	}


}
