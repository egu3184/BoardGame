package com.egu.boot.BoardGame.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.egu.boot.BoardGame.model.dto.PaymentDto.PaymentRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Enumerated(EnumType.STRING)
	private PaymentMethodType paymentMethod;	//결제방식
	
	private long TotPrice; //결제 금액
	
	@Enumerated(EnumType.STRING)
	private PaymentStatusType payStatus; //결제 상태
	
//	private LocalDateTime payTime; //결제 일자
//	private String cardNum; //카드 번호
//	private String cardHolder; //카드 소유주

	private LocalDateTime depositCompletionDateTime;	//입금완료시각

	private LocalDateTime depositDueDateTime;	//입금마감시각

	private String depositorName; //입금자 이름
	
	private long depositPrice; //예약금 금액
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bankAccountId")
	private BankAccount bankAccount;


	public  Payment(PaymentRequestDto dto) {
		this.TotPrice = dto.getTotPrice();
		this.depositorName = dto.getDepositorName();
		this.depositPrice = dto.getDepositPrice();
	}
	
	
	
}
