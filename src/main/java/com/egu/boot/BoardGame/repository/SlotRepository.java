package com.egu.boot.BoardGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.Slot;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

	
	
}