package com.egu.boot.BoardGame.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
import com.egu.boot.BoardGame.repository.FindSlotRepository;
import com.egu.boot.BoardGame.repository.SlotRepository;
import com.egu.boot.BoardGame.repository.ThemeRepository;

@Service
public class SlotService {

	@Autowired
	private FindSlotRepository findSlotRepository;
	
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
		Branch branch = branchRepository.findById(slotDto.getBranchId()).orElseThrow(()->{
			throw new CustomException(ErrorCode.BRANCH_NOT_FOUND);
		});
		Slot slot = Slot.builder()
							.branch(branch)
							.isOpened(false)
							.isReserved(false)
							.isShowed(false)
							.reservation(null)
							.slotDate(slotDto.getSlotDate())
							.slotTime(slotDto.getSlotTime())
							.theme(theme)
							.build();

		slotRepository.save(slot);
	}

	//슬롯 수정
	@Transactional
	public void 슬롯수정(SlotSaveRequestDto requestSlot, int id) {
		
		Slot slot = slotRepository.findById(id).orElseThrow(()->{
			throw new CustomException(ErrorCode.SLOT_NOT_FOUND);
		}); 
		//slot.setTheme(requestSlot.getTheme());
		slot.setOpened(requestSlot.isOpened());
		//slot.setBranch(requestSlot.getBranch());
		slot.setShowed(requestSlot.isShowed());
		slot.setReserved(requestSlot.isReserved());
		//slot.setSlotDate(requestSlot.getSlotDate());
		//slot.setSlotTime(requestSlot.getSlotTime());
	
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
	public List<Slot> 슬롯현황조회(LocalDate slotDate, int branchId, int themeId) {
		Theme theme = themeRepository.findById(themeId).orElseThrow(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
		});
		Branch branch = branchRepository.findById(branchId).orElseThrow(()->{
			throw new CustomException(ErrorCode.BRANCH_NOT_FOUND);
		});
		List<Slot> list = slotRepository.findAllBySlotDateAndBranchAndTheme(slotDate, branch, theme);
		return list;
	}

	
	@Transactional
	public void 슬롯일괄등록(SlotSaveRequestDto slotDto) {
		String openTime = "10:00:00";
		String endTime = "23:30:00";
		LocalTime slotOpenTime = LocalTime.parse(openTime);
		LocalTime slotCloseTime = LocalTime.parse(endTime);
		LocalTime slotTime = slotOpenTime;
		
		while(slotTime.isBefore(slotCloseTime)) {	//Open시간이 Close시간보다 이전이니?
			slotDto.setSlotTime(slotTime);
			슬롯등록(slotDto);
			slotTime = slotTime.plusHours(1).plusMinutes(30);		//1시간 30분 추가	
		}
	}
	
	
	@Transactional
	public LocalDate 슬롯오픈날짜조회(int branchId, int themeId) {
		Theme theme = themeRepository.findById(themeId).orElseThrow(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
		});
		Branch branch = branchRepository.findById(branchId).orElseThrow(()->{
			throw new CustomException(ErrorCode.BRANCH_NOT_FOUND);
		});
		List<LocalDate> list = findSlotRepository.getLatestOpenedSlotDate(branch, theme);
		LocalDate date = list.get(0);
		return date;
	}

	@Transactional
	public List<LocalDate> 빈날짜찾기(LocalDate minDate, LocalDate maxDate) {
		List<LocalDate> list =  findSlotRepository.findNotShowedDate(minDate, maxDate);
		if(list.isEmpty()) {
			System.out.println("서비스 메서드 : 리스트 못 받음. ");
		}
		for(LocalDate dd : list) {
			System.out.println("DB에서 받은 리스트");
			System.out.println(dd);
		}
		
		List<LocalDate> responseDateList = new ArrayList<>();
		LocalDate responseDate = minDate;
		while(true) {
			responseDateList.add(responseDate);
			responseDate = responseDate.plusDays(1);
			if(responseDate.isAfter(maxDate)) break;
		};
		for(LocalDate ss : responseDateList) {
			System.out.println("하루씩 늘렸음.");
			System.out.println(ss);
		}
		
		
		//중복은 없음.
		for(LocalDate date : list) 
			responseDateList.remove(date);
//		for(LocalDate date : responseDateList) 
//		System.out.println(date);	
		return responseDateList;
	}

}
