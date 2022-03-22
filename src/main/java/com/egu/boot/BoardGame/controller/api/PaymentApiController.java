package com.egu.boot.BoardGame.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Payment;
import com.egu.boot.BoardGame.model.api.SingleResult;
import com.egu.boot.BoardGame.model.dto.PaymentDto.PaymentRequestDto;
import com.egu.boot.BoardGame.model.dto.PaymentDto.PaymentResponseDto;
import com.egu.boot.BoardGame.service.PaymentService;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentApiController {

	private final PaymentService paymentService;
	private final ResponseService responseService;
	
	@PostMapping("/payment")
	public SingleResult<PaymentResponseDto> savePayment(@RequestBody PaymentRequestDto requestDto){
		PaymentResponseDto responseDto = paymentService.결제정보저장(requestDto);
		return responseService.getSingleResult(responseDto);
	}
	
	
}
