package com.egu.boot.BoardGame.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {

	private int userId;
	private int slotId;
	private int persons;
	private LocalDateTime reservationTime;
	private int payment;
}
