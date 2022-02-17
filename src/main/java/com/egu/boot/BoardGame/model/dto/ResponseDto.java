package com.egu.boot.BoardGame.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

	
	ArrayList<String> branchNameList;
	
	ArrayList<String> themeNameList;
	
	ArrayList<LocalTime> slotTimeList;
	
	
	
	
	
}
