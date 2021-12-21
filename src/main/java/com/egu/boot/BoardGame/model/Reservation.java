package com.egu.boot.BoardGame.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private int persons;

	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime reservationTime;
	
	@Column(nullable = false)
	private int payment;
	
	@Column(nullable = false)
	private boolean isPaid;
	
	@OneToOne
	@JoinColumn(name="slotId")
	private Slot slot;
	
	@Column(nullable = false)
	private String guestName;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	
}
