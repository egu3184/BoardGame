package com.egu.boot.BoardGame.model.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;

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
	
	public static class ReservationResponseDto{
		private int id;
		
		public ReservationResponseDto(Reservation reserv) {
			this.id = reserv.getId();
		}
	}
	
	
}
