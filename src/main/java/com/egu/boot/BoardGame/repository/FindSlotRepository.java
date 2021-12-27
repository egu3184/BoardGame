package com.egu.boot.BoardGame.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.egu.boot.BoardGame.model.QSlot;
import com.egu.boot.BoardGame.model.Slot;

public interface FindSlotRepository {

	QSlot slot = QSlot.slot;
	
	List<Slot> searchSlot(Integer id, 
										LocalDateTime startTime, 
										LocalDateTime endTime,
										Boolean isOpened,
										Boolean isReserved
										);
	
}
