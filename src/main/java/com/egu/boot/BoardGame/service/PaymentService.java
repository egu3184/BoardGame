package com.egu.boot.BoardGame.service;

import javax.transaction.Transactional;

import com.egu.boot.BoardGame.model.PaymentMethodType;
import com.egu.boot.BoardGame.model.PaymentStatusType;
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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepositroy;
	private final BankAccountRepository accountRepository;

	@Transactional
	public PaymentResponseDto 결제정보저장(PaymentRequestDto dto) {
		BankAccount account = accountRepository.findById(dto.getBankAccountId()).<CustomException>orElseThrow(()->{
			throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
		});
		Payment payment = new Payment(dto);
		payment.setBankAccount(account);
		payment.setDepositDueDateTime(LocalDateTime.now().plusMinutes(30));
		switch (dto.getPaymentMethod()) {
			case "onSite":
				payment.setPaymentMethod(PaymentMethodType.OnSite);
				payment.setPayStatus(PaymentStatusType.DepositWaiting);
				break;
		}
		payment.setTotPrice(dto.getTotPrice());
		payment.setDepositorName(dto.getDepositorName());
		payment.setDepositPrice(dto.getDepositPrice());

		Payment pay = paymentRepositroy.save(payment);
		return new PaymentResponseDto(pay);
	}
	
	
	
	
}
