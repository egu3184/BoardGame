package com.egu.boot.BoardGame.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Theme theme;
	
	@Column(nullable = false)
	private LocalDate slotDate;
	
	@Column(nullable = false)
	private LocalTime slotTime;
	
	@Column(nullable = false)
	private boolean isOpened;
	
	@Column(nullable = false)
	private boolean isReserved;
	
}
