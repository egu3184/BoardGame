package com.egu.boot.BoardGame.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.model.Payment;
import com.egu.boot.BoardGame.model.dto.PaymentDto.PaymentResponseDto;
import com.egu.boot.BoardGame.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepositroy;

	@Transactional
	public PaymentResponseDto 결제정보저장(Payment payment) {
		Payment pay = paymentRepositroy.save(payment);
		return new PaymentResponseDto(pay);
	}
	
	
	
	
}
