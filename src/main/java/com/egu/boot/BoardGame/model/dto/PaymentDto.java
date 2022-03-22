package com.egu.boot.BoardGame.model.dto;

import com.egu.boot.BoardGame.model.Payment;

import lombok.Getter;
import lombok.Setter;

public class PaymentDto {

	@Getter
	@Setter
	public static class PaymentRequestDto{
		private String paymentMethod;
		private long totPrice;
		private String depositorName;
		private long depositPrice;
		private Integer bankAccountId;
	
	}
	
	
	@Getter
	public static class PaymentResponseDto{
		private int id;
		
		public PaymentResponseDto(Payment payment){
			this.id = payment.getId();
		}
	}
	
}
