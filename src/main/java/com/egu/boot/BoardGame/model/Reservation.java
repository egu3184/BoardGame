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

import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationRequestDto;

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
	private Integer id;
	
	@Column(nullable = false)
	private Integer numUsers;		//유저수

	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime reservationTime;		//예약 시각
		
	@Column(nullable = false)
	private String bookerName;
	
	@Column(nullable = false)
	private String phoneNumber;

	@Column
	private String email;
	
	//조인할 지점 컬럼 추가
	@OneToOne
	@JoinColumn(name = "branchId")
	private Branch branch;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="slotId")
	private Slot slot;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="themeId")
	private Theme theme;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="paymentId")
	private Payment payment;
	
	@Column(nullable = false)
	private boolean privacyAgree;	//개인 정보 이용 및 수집 동의
	
	@Column(nullable = false)
	private boolean conditionsAgree; //이용 약관 동의
	
	
	public Reservation(ReservationRequestDto reservationRequestDto
//			, Payment payment, Branch branch, Slot slot, Theme theme
			) {
		 this.bookerName = reservationRequestDto.getBookName();
		 this.numUsers = reservationRequestDto.getNumUsers(); 
		 this.privacyAgree = reservationRequestDto.isPrivacyAgree(); 
		 this.conditionsAgree = reservationRequestDto.isPrivacyAgree();
//		 this.payment = payment;
//		 this.slot = slot;
//		 this.theme = theme;
//		 this.branch = branch;
		 this.phoneNumber = reservationRequestDto.getPhoneNum();
		
	}
	
	
	
}
