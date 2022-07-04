package com.egu.boot.BoardGame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.Theme;
import com.egu.boot.BoardGame.model.dto.ResponseDto;
import com.egu.boot.BoardGame.model.dto.ThemeDto;
import com.egu.boot.BoardGame.model.dto.ThemeDto.ThemeResponseDto;
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
		Theme theme =  themeRepository.findById(id).<CustomException>orElseThrow(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
		});
		theme.setThemeName(requestTheme.getThemeName());
		theme.setDescription(requestTheme.getDescription());
		theme.setMinimumCapacity(requestTheme.getMinimumCapacity());
		theme.setMaximumCapacity(requestTheme.getMaximumCapacity());
		theme.setPlayTime(requestTheme.getPlayTime());
		theme.setDifficulty(requestTheme.getDifficulty());
		theme.setGenre(requestTheme.getGenre());
		theme.setIsOpened(true);
	}
	
	@Transactional
	public void 테마삭제(int id) {
		themeRepository.findById(id).<CustomException>orElseThrow(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
		});
		themeRepository.deleteById(id);
	}

	@Transactional
	public List<ThemeResponseDto> 테마리스트() {
		List<Theme> themes = themeRepository.findAll();
		List<ThemeResponseDto> dtoList = new ArrayList<ThemeResponseDto>();
		for(Theme theme:themes) {
			if(theme.getIsOpened())
				dtoList.add(new ThemeDto.ThemeResponseDto(theme));
		}
		return dtoList;
	}

	@Transactional
	public Theme 테마조회(int id) {
		return themeRepository.findById(id).orElseGet(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
		});
	}
	
	
	
	
	
}
