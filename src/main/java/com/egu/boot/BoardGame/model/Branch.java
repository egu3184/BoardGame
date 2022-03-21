package com.egu.boot.BoardGame.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String branchName;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String adress;
	
	@Column
	private Integer depositPrice;
	
	
	
}
