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

	@Transactional
	public void 테마수정(int id, Theme requestTheme) {
		Theme theme =  themeRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("등록된 테마가 아닙니다.");
		});
		theme.setThemeName(requestTheme.getThemeName());
		theme.setDescription(requestTheme.getDescription());
		theme.setMinimumCapacity(requestTheme.getMinimumCapacity());
		theme.setMaximumCapacity(requestTheme.getMaximumCapacity());
		theme.setPlayTime(requestTheme.getPlayTime());
		theme.setDifficulty(requestTheme.getDifficulty());
		theme.setGenre(requestTheme.getGenre());
	}
	
	@Transactional
	public void 테마삭제(int id) {
		Theme theme =  themeRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("등록된 테마가 아닙니다.");
		});
		themeRepository.deleteById(id);
	}
	
	
	
	
	
}
