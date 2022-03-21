package com.egu.boot.BoardGame.model.dto;

import javax.persistence.Column;

import com.egu.boot.BoardGame.model.BankAccount;

import lombok.Getter;

public class BankAccountDto {

	@Getter
	public static class BankAccountResponseDto{
		
		private int id;
		private String bankName;
		private String bankAccountNumber;
		private String bankAccountHolder;
		
		public  BankAccountResponseDto(BankAccount ba) {
			this.id = ba.getId();
			this.bankName = ba.getBankName();
			this.bankAccountNumber = ba.getBankAccountNumber();
			this.bankAccountHolder = ba.getBankAccountHolder();
		}
	}
	
}
