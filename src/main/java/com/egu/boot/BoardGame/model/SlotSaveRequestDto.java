package com.egu.boot.BoardGame.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotSaveRequestDto {

	int themeId;
	LocalDateTime slotDateTime;
	
}
