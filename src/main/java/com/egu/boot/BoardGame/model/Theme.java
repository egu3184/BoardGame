package com.egu.boot.BoardGame.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Theme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String themeName;
	
	@Lob
	private String description;
	
	private String minimumCapacity;
	
	private String maximumCapacity;
	
	private String playTime;
	
	private String admissionFee;
	
	private String difficulty;
	
	private String genre;
	
	@OneToMany(mappedBy = "theme")
	@JsonIgnoreProperties({"reservation"})
	private List<Slot> slots;
	
	
	
	
}
