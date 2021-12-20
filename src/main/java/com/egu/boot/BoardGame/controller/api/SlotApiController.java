package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.ListResult;
import com.egu.boot.BoardGame.model.api.SingleResult;
import com.egu.boot.BoardGame.service.SlotService;
import com.egu.boot.BoardGame.service.api.ResponseService;

@RestController
public class SlotApiController {

	@Autowired
	SlotService slotService;

	@Autowired
	ResponseService responseService;
	
	//슬롯 등록
	@PostMapping("/slots")
	public CommonResult saveSlot(@RequestBody SlotSaveRequestDto slotDto) {
		slotService.슬롯등록(slotDto);
		return responseService.getSuccessResult();
	}
	
	//슬롯 수정 
	@PutMapping("/slots/{id}")
	public CommonResult editSlot(@PathVariable int id, @RequestBody Slot requestSlot) {
		slotService.슬롯수정(requestSlot,id);
		return responseService.getSuccessResult();
	}
	
	//슬롯 삭제
	@DeleteMapping("/slots/{id}")
	public CommonResult deleteSlot(@PathVariable int id) {
		slotService.슬롯삭제(id);
		return responseService.getSuccessResult();
	}
	
	@GetMapping("/slots/{id}")
	public SingleResult<Slot> selectSlot(@PathVariable int id){
		Slot slot = slotService.슬롯조회(id);
		return responseService.getSingleResult(slot);
	}
	
	@GetMapping("/slots")
	public ListResult<Slot> selectListSlot(Pageable pageable){
		Page<Slot> list = slotService.슬롯리스트조회(pageable);
		return responseService.getPageListResult(list);
	}
	
}
