package com.egu.boot.BoardGame.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.BankAccount;
import com.egu.boot.BoardGame.model.Payment;
import com.egu.boot.BoardGame.model.dto.PaymentDto.PaymentRequestDto;
import com.egu.boot.BoardGame.model.dto.PaymentDto.PaymentResponseDto;
import com.egu.boot.BoardGame.repository.BankAccountRepository;
import com.egu.boot.BoardGame.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepositroy;
	private final BankAccountRepository accountRepository;

	@Transactional
	public PaymentResponseDto 결제정보저장(PaymentRequestDto dto) {
		BankAccount account = accountRepository.findById(dto.getBankAccountId()).orElseThrow(()->{
			throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
		});
		Payment payment = new Payment(dto);
		payment.setBankAccount(account);
		
		Payment pay = paymentRepositroy.save(payment);
		return new PaymentResponseDto(pay);
	}
	
	
	
	
}
