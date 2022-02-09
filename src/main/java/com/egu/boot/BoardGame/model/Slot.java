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
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="themeId")
	@JsonIgnoreProperties({"slots"})
	private Theme theme;
	
	private LocalDate slotDate;
	
	private LocalTime slotTime;

	@Column(nullable = false)
	private boolean isOpened;
	
	@Column(nullable = false)
	private boolean isShowed;
	
	@Column(nullable = false)
	private boolean isReserved;
	
	@OneToOne(mappedBy = "slot", fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"slots"})
	private Reservation reservation;
	
	@Version
	private int version;
	
	@OneToOne
	@JoinColumn(name="branchId")
	private Branch branch;
	
}
