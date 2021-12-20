package com.egu.boot.BoardGame.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.Slot;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

	
	Page<Slot> findAllByIsOpenedAndIsReserved(boolean opened, boolean reserved, Pageable pageable);
	
}
