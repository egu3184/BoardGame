package com.egu.boot.BoardGame.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Data
@Entity
public class Theme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ThemeName themeName;
	
	@Lob
	private String description;
	
	private String minimumCapacity;
	
	private String maximumCapacity;
	
	private String playTime;
	
	private String admissionFee;
	
	private String difficulty;
	
	private String Genre;
	
	
	
	
}
