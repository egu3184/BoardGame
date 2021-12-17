package com.egu.boot.BoardGame.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.SlotSaveRequestDto;
import com.egu.boot.BoardGame.model.Theme;
import com.egu.boot.BoardGame.repository.SlotRepository;
import com.egu.boot.BoardGame.repository.ThemeRepository;

@Service
public class SlotService {

	
	@Autowired
	private SlotRepository slotRepository;
	
	@Autowired
	private ThemeRepository themeRepository;
	
	@Transactional
	public void 슬롯등록(SlotSaveRequestDto slotDto) {
		
		Theme theme = themeRepository.findById(slotDto.getThemeId()).orElseThrow(()->{
			return new IllegalArgumentException("등록된 테마가 아닙니다.");
		});
		
		Slot slot = new Slot();
		
		slot.setTheme(theme);
		slot.setSlotDate(slotDto.getSlotDate());
		slot.setSlotTime(slotDto.getSlotTime());
		slot.setOpened(true);
		slot.setReserved(false);
		slotRepository.save(slot);
		
	}
	
}
