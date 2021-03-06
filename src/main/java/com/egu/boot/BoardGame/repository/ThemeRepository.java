package com.egu.boot.BoardGame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

	Optional<Theme> findByThemeName(String themeName);	
	
}
