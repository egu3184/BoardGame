package com.egu.boot.BoardGame.service;

import java.time.LocalDateTime;

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
	
	//슬롯 등록
	@Transactional
	public void 슬롯등록(SlotSaveRequestDto slotDto) {
		Theme theme = themeRepository.findById(slotDto.getThemeId()).orElseThrow(()->{
			return new IllegalArgumentException("등록된 테마가 아닙니다.");
		});
		Slot slot = new Slot();
		slot.setTheme(theme);
		slot.setSlotDateTime(slotDto.getSlotDateTime());
		slot.setOpened(true);
		slot.setReserved(false);
		slotRepository.save(slot);
	}

	//슬롯 수정
	@Transactional
	public void 슬롯수정(Slot requestSlot, int id) {
		
		Slot slot = slotRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("등록된 슬롯이 아닙니다.");
		});
		slot.setTheme(requestSlot.getTheme());
		slot.setOpened(requestSlot.isOpened());
		slot.setReserved(requestSlot.isReserved());
		slot.setSlotDateTime(requestSlot.getSlotDateTime());
	
	}

	//슬롯 삭제 
	@Transactional
	public void 슬롯삭제(int id) {
		slotRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("등록된 슬롯이 아닙니다.");
		});
		slotRepository.deleteById(id);
	}

	//슬롯 조회
	@Transactional
	public Slot 슬롯조회(int id) {
		return slotRepository.findById(id).orElseGet(null);
	}
	
	//슬롯 검색
	@Transactional
	public Page<Slot> 슬롯검색(int id, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
		System.out.println("슬롯 검색 호출됨");
		return slotRepository.findAllBySlotDateTimeBetween(startDateTime, endDateTime, pageable);
	}

	//예약 가능 슬롯 조회( opened & not reserved only )
	@Transactional
	public Page<Slot> 예약가능슬롯조회(Pageable pageable) {
		return slotRepository.findAllByIsOpenedAndIsReserved(true, false, pageable);
	}
	
	//슬롯 전체 조회
	public Page<Slot> 모든슬롯조회(Pageable pageable) {
		return slotRepository.findAll(pageable);
	}
	
	
}
