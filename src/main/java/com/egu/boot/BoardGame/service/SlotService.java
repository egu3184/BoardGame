package com.egu.boot.BoardGame.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.Theme;
import com.egu.boot.BoardGame.model.dto.SlotSaveRequestDto;
import com.egu.boot.BoardGame.repository.BranchRepository;
import com.egu.boot.BoardGame.repository.SlotRepository;
import com.egu.boot.BoardGame.repository.ThemeRepository;

@Service
public class SlotService {

	
	@Autowired
	private SlotRepository slotRepository;
	
	@Autowired
	private ThemeRepository themeRepository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	//슬롯 등록
	@Transactional
	public void 슬롯등록(SlotSaveRequestDto slotDto) {
		Theme theme = themeRepository.findById(slotDto.getThemeId()).orElseThrow(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
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
			throw new CustomException(ErrorCode.SLOT_NOT_FOUND);
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
			throw new CustomException(ErrorCode.SLOT_NOT_FOUND);
		});
		slotRepository.deleteById(id);
	}

	//슬롯 조회
	@Transactional
	public Slot 슬롯조회(int id) {
		Slot slot = slotRepository.findById(id).orElseThrow(()->{
			throw new CustomException(ErrorCode.SLOT_NOT_FOUND);
		});
		return slot;
	}
	
	//슬롯 검색
	public List<Slot> 슬롯현황조회(LocalDate slotDate, String branchName, String themeName) {
		System.out.println(branchName + themeName);
		Theme theme = themeRepository.findByThemeName(themeName).orElseThrow(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
		});
		System.out.println("themeName =" +theme.getId());
		Branch branch = branchRepository.findAllBybranchName(branchName).orElseThrow(()->{
			throw new CustomException(ErrorCode.BRANCH_NOT_FOUND);
		});
		System.out.println(branch.getId());
		List<Slot> list = slotRepository.findAllBySlotDateAndBranchAndTheme(slotDate, branch, theme);

		return list;
	}

}
