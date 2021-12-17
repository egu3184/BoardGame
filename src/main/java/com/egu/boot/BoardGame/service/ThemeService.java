package com.egu.boot.BoardGame.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.model.Theme;
import com.egu.boot.BoardGame.repository.ThemeRepository;

@Service
public class ThemeService {

	@Autowired
	private ThemeRepository themeRepository;
	
	@Transactional
	public void 테마저장(Theme theme) {
		themeRepository.save(theme);
	}
	
	
	
	
	
}
