package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.SlotSaveRequestDto;
import com.egu.boot.BoardGame.service.SlotService;

@RestController
public class SlotApiController {

	@Autowired
	SlotService slotService;
	
	@PostMapping("/slot/save")
	public String saveSlot(@RequestBody SlotSaveRequestDto slotDto) {
		System.out.println("save 슬롯 호출 됨.");
		System.out.println("themeId"+slotDto.getThemeId());
		System.out.println("slotDate = "+slotDto.getSlotDate());
		System.out.println("slotTime="+slotDto.getSlotTime());
		 
		slotService.슬롯등록(slotDto);
		
		return "슬롯 등록 완료";
	}
	
}
