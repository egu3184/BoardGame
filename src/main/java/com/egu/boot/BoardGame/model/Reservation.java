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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="slotId")
	private Slot slot;
	
	@Column(nullable = false)
	private String bookerName;
	
	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String email;
	
	//조인할 지점 컬럼 추가
	@OneToOne
	@JoinColumn(name = "branchId")
	private Branch branch;
}
