package com.egu.boot.BoardGame.model.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {
	
	private Integer userId;
	private int slotId;
	private int persons;
	private int payment;
	
	//비회원 예약 요청시 필요한 컬럼
	private String bookerName;
	//private String password; 예약확인용 비밀번호
	private String phoneNumber;
	private String email;
	
	//예약 찾기에 필요한 컬럼
	private Integer reservationId;
	
	
}
