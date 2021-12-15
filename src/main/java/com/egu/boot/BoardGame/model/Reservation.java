package com.egu.boot.BoardGame.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private int persons;

	@OneToOne
	private Slot slotId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Timestamp reservationTime;
	
	@Column(nullable = false)
	private int payment;
	
	@Column(nullable = false)
	private boolean isPaid;
	
	
	
}
