package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	//슬롯 등록
	@PostMapping("/slots")
	public String saveSlot(@RequestBody SlotSaveRequestDto slotDto) {
		slotService.슬롯등록(slotDto);
		return "슬롯 등록 완료";
	}
	
	//슬롯 수정 
	@PutMapping("/slots/{id}")
	public String editSlot(@PathVariable int id, @RequestBody Slot requestSlot) {
		slotService.슬롯수정(requestSlot,id);
		return "슬롯 수정 완료";
	}
	
	//슬롯 삭제
	@DeleteMapping("/slots/{id}")
	public String deleteSlot(@PathVariable int id) {
		slotService.슬롯삭제(id);
		return "슬롯 삭제 완료";
	}
	
	
}
