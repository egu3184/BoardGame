package com.egu.boot.BoardGame.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String bankName;
	
	@Column
	private String bankAccountNumber;
	
	@Column
	private String bankAccountHolder;
	
	@ManyToOne
	@JoinColumn(name="branchId")
	private Branch branch;
	
}
