package com.egu.boot.BoardGame.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.model.QSlot;
import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.Theme;

public interface FindSlotRepository {

	QSlot slot = QSlot.slot;
	
	List<Slot> searchSlot(Integer id, 
										LocalDate startDate, 
										LocalDate endDate,
										Boolean isOpened,
										Boolean isReserved
										);
	
	 List<LocalDate> getLatestOpenedSlotDate(Branch branch, Theme theme);

	List<LocalDate> findNotShowedDate(LocalDate minDate, LocalDate maxDate);
	
}
