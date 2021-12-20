package com.egu.boot.BoardGame.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50, nullable = false)
	private String username;
	
	@Column(length = 300)
	private String password;
	
	@Column(length = 200, unique = true, nullable=false)
	private String email;
	
	@Column
	private LocalDateTime createDate;
	
	@Column(length = 30, nullable = false)
	private String phoneNumber;
	
	private String provider;
	
	private String provider_id;

	private boolean isRegisterd;
	
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
}
