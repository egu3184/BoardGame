package com.egu.boot.BoardGame.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.egu.boot.BoardGame.model.Slot;

import lombok.Getter;

public class SlotDto {

	@Getter
	public static class SlotResponseDto{
		private Integer slotId;
		private LocalDate slotDate;
		private LocalTime slotTime;
		private boolean isShowed;
		private boolean isReserved;
		private boolean isOpened;
	
		public SlotResponseDto(Slot slot){
			this.slotId = slot.getId();
			this.slotDate = slot.getSlotDate();
			this.slotTime = slot.getSlotTime();
			this.isOpened = slot.isOpened();
			this.isReserved = slot.isReserved();
			this.isShowed = slot.isShowed();
		}
	}
	
	
}
