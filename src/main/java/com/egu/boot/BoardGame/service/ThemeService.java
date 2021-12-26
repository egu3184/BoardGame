package com.egu.boot.BoardGame.service;

import java.util.List;

import javax.transaction.Transactional;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
			//throw new IllegalArgumentException("등록된 테마가 아닙니다.");
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
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
		themeRepository.findById(id).orElseThrow(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
		});
		themeRepository.deleteById(id);
	}

	@Transactional
	public Page<Theme> 테마리스트(Pageable pageable) {
		return themeRepository.findAll(pageable);
	}

	@Transactional
	public Theme 테마조회(int id) {
		return themeRepository.findById(id).orElseGet(null);
	}
	
	
	
	
	
}
