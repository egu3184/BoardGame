package com.egu.boot.BoardGame.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="themeId")
	@JsonIgnoreProperties({"slots"})
	private Theme theme;
	
	@Column(nullable = false)
	private LocalDateTime slotDateTime;

	@Column(nullable = false)
	private boolean isOpened;
	
	@Column(nullable = false)
	private boolean isReserved;
	
	@OneToOne(mappedBy = "slot")
	@JsonIgnoreProperties({"slots"})
	private Reservation reservation;
	
}
