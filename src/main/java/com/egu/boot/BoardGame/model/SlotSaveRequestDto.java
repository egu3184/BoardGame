package com.egu.boot.BoardGame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotSaveRequestDto {

	int themeId;
	String slotDate;
	String slotTime;
	
}
