package com.egu.boot.BoardGame.controller.api;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.ListResult;
import com.egu.boot.BoardGame.model.api.SingleResult;
import com.egu.boot.BoardGame.model.dto.SlotSaveRequestDto;
import com.egu.boot.BoardGame.repository.FindSlotRepository;
import com.egu.boot.BoardGame.service.SlotService;
import com.egu.boot.BoardGame.service.api.ResponseService;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", allowedHeaders = "*")
public class SlotApiController {
	
	private final FindSlotRepository findSlotRepository;
	private final SlotService slotService;
	private final ResponseService responseService;
	
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
	
	//슬롯 검색 리스트 조회
	@GetMapping(value="/slots")
	public ListResult<Slot> findSlot(
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
			@RequestParam(value = "startDateTime", required = false) LocalDateTime startDateTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
			@RequestParam(value = "endDateTime",  required = false) LocalDateTime endDateTime,
			@RequestParam(value="id", required = false) Integer id, 
			@RequestParam(value="isOpened", required=false) Boolean isOpened,
			@RequestParam(value="isReserved", required=false) Boolean isReserved
			){ 
		System.out.println("isOpened = "+isOpened);
		System.out.println("isReserved = "+isReserved);
		List<Slot> list=  findSlotRepository.searchSlot(id, startDateTime, endDateTime, isOpened, isReserved);
		return responseService.getListResult(list);
	}
	
	//슬롯 단일 조회
	@GetMapping("/slots/{id}")
	public SingleResult<Slot> findSlot(@PathVariable int id){
		Slot slot = slotService.슬롯조회(id);
		return responseService.getSingleResult(slot);
	}
	
	//슬롯 전체 조회
	//추후 권한 설정 필요
	/*@GetMapping("/slots")
	public ListResult<Slot> findAllSlot(Pageable pageable
			//, @Authentication PrincipalDetail principal
			){
		//	if(pricipal.getRole.equals("ROLE_ADMIN"){
		//		Page<Slot> list = slotService.슬롯전체조회(pageable);
		// }else{
		//		Page<Slot> list = slotService.예약가능슬롯조회(pageable);
		//}
		Page<Slot> list = slotService.모든슬롯조회(pageable);
		return responseService.getPageListResult(list);
	}
	*/
	
}
