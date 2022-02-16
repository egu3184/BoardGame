package com.egu.boot.BoardGame.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.Theme;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

	
	Page<Slot> findAllByIsOpenedAndIsReserved(boolean opened, boolean reserved, Pageable pageable);
	
	//Page<Slot> findAllBySlotDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
	//List<Slot> findAllbySlotDate(LocalDate SlotDate); 
	 
	List<Slot> findAllBySlotDateAndBranchAndTheme(LocalDate slotDate, Branch branch, Theme theme);
	//객체를 넣어도 찾을 수 있다!
	
	
}
