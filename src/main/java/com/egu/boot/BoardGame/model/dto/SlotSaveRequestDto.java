package com.egu.boot.BoardGame.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotSaveRequestDto {

	int themeId;
	
	int branchId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate slotDate;
	
	@DateTimeFormat(pattern = "HH:mm:ss")
	LocalTime slotTime;
	
	boolean isOpened;
	
	boolean isShowed;
	
	boolean isReserved;
	

	
}
