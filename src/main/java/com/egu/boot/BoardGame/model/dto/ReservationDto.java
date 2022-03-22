package com.egu.boot.BoardGame.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;

import com.egu.boot.BoardGame.model.PaymentMethodType;
import com.egu.boot.BoardGame.model.PaymentStatusType;
import com.egu.boot.BoardGame.model.Reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReservationDto {
	
	@Getter
	@Setter
	public static class ReservationRequestDto{
		private String bookName; 
		private int slotId ;
		private int branchId; 
		private int themeId; 
		private int numUsers; 
		private boolean privacyAgree; 
		private boolean conditionsAgree;
		private int paymentId;
		private String phoneNum;
		
	}
	
	@Getter
	public static class ReservationResponseDto{
		private int id;
		private Integer numUsers;
		private String bookerName;
		private String themeName;
		private String branchName;
		private PaymentMethodType paymentMethod;
		private String bankAccountHolder;
		private String bankAccountNumber;
		private String bankName;
		private String depositorName;
		private long depositPrice;
		private PaymentStatusType paymentStatus;
		private long totPrice;
		private LocalDate slotDate;
		private LocalTime slotTime;
		
		
		public ReservationResponseDto(Reservation reserv) {
			//reservation
			this.id = reserv.getId();
			this.numUsers = reserv.getNumUsers();
			this.bookerName = reserv.getBookerName();
			//theme
			this.themeName = reserv.getTheme().getThemeName();
			//branch
			this.branchName = reserv.getBranch().getBranchName();
			//bankAccount
			this.bankAccountHolder = reserv.getPayment().getBankAccount().getBankAccountHolder();
			this.bankAccountNumber = reserv.getPayment().getBankAccount().getBankAccountNumber();
			this.bankName = reserv.getPayment().getBankAccount().getBankName();
			//slot
			this.slotDate = reserv.getSlot().getSlotDate();
			this.slotTime = reserv.getSlot().getSlotTime();
			//payment
			this.paymentMethod = reserv.getPayment().getPaymentMethod();
			this.depositorName = reserv.getPayment().getDepositorName();
			this.depositPrice = reserv.getPayment().getDepositPrice();
			this.paymentStatus = reserv.getPayment().getPayStatus();
			this.totPrice = reserv.getPayment().getTotPrice();
			
		}
	}
	
	
}
