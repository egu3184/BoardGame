package com.egu.boot.BoardGame.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	//수정 - 미완
	@Transactional
	public void 슬롯수정(Slot requestSlot, int id) {
		
		Slot slot = slotRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("등록된 슬롯이 아닙니다.");
		});
		//슬롯에서 무얼 무얼 변경 가능할 것인가? 테마도 변경 가능?
		
		slot.setOpened(requestSlot.isOpened());
		slot.setReserved(requestSlot.isReserved());
		slot.setSlotDate(requestSlot.getSlotDate());
		slot.setSlotTime(requestSlot.getSlotTime());
	}

	//삭제 
	@Transactional
	public void 슬롯삭제(int id) {
		Slot slot = slotRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("등록된 슬롯이 아닙니다.");
		});
		slotRepository.deleteById(id);
	}

	@Transactional
	public Slot 슬롯조회(int id) {
		return slotRepository.findById(id).orElseGet(null);
	}

	@Transactional
	public Page<Slot> 슬롯리스트조회(Pageable pageable) {
		return slotRepository.findAllByIsOpenedAndIsReserved(true, false, pageable);
	}
	
	
	
	
}
