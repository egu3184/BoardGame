package com.egu.boot.BoardGame.controller.api;

import java.security.Principal;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.ListResult;
import com.egu.boot.BoardGame.model.api.SingleResult;
import com.egu.boot.BoardGame.model.dto.ResponseDto;
import com.egu.boot.BoardGame.model.dto.SlotDto;
import com.egu.boot.BoardGame.model.dto.SlotSaveRequestDto;
import com.egu.boot.BoardGame.model.dto.SlotDto.SlotResponseDto;
import com.egu.boot.BoardGame.repository.FindSlotRepository;
import com.egu.boot.BoardGame.service.SlotService;
import com.egu.boot.BoardGame.service.api.ResponseService;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SlotApiController {
	
	private final FindSlotRepository findSlotRepository;
	private final SlotService slotService;
	private final ResponseService responseService;
	
	//?????? ??????
	@PostMapping("/slots")
	public CommonResult saveSlot(@RequestBody SlotSaveRequestDto slotDto) {
		slotService.????????????(slotDto); 
		return responseService.getSuccessResult();
	} 
	
	//?????? ?????? ?????? ?????? ?????? (????????? ?????? ?????????)
	@PostMapping("/slots/all")
	public CommonResult saveAllSlot(@RequestBody SlotSaveRequestDto slotDto ) {
		slotService.??????????????????(slotDto);
		return responseService.getSuccessResult();
	}
	
	//?????? ?????? ??????
	@GetMapping("/slots/{id}")
	public SingleResult<SlotResponseDto> findSlot(@PathVariable int id){
		SlotResponseDto dto = slotService.????????????(id);
		return responseService.getSingleResult(dto);
	}
	///
	
	
	//?????? ?????? 
	@PutMapping("/slots/{id}")
	public CommonResult editSlot(@PathVariable int id, @RequestBody SlotSaveRequestDto requestSlot) {
		slotService.????????????(requestSlot,id);
		return responseService.getSuccessResult();
	}
	
	//?????? ??????
	@DeleteMapping("/slots/{id}")
	public CommonResult deleteSlot(@PathVariable int id) {
		slotService.????????????(id);
		return responseService.getSuccessResult();
	}
	
	//?????? ?????? ????????? ??????
//	@GetMapping(value="/slots")
//	public ListResult<Slot> findSlot(
//			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//			@RequestParam(value = "startDateTime", required = false) LocalDateTime startDateTime,
//			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//			@RequestParam(value = "endDateTime",  required = false) LocalDateTime endDateTime,
//			@RequestParam(value="id", required = false) Integer id, 
//			@RequestParam(value="isOpened", required=false) Boolean isOpened,
//			@RequestParam(value="isReserved", required=false) Boolean isReserved
//			){ 
//		System.out.println("isOpened = "+isOpened);
//		System.out.println("isReserved = "+isReserved);
//		List<Slot> list=  findSlotRepository.searchSlot(id, startDateTime, endDateTime, isOpened, isReserved);
//		return responseService.getListResult(list);
//	}
	
	@GetMapping(value = "/slots")
	public ListResult<SlotResponseDto> findSlot(
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			@RequestParam(value="slotDate") LocalDate slotDate, 
			@RequestParam(value="branchId") String branchId,
			@RequestParam(value="themeId") String themeId
			){
		List<SlotResponseDto> dto =  slotService.??????????????????(slotDate, Integer.parseInt(branchId), Integer.parseInt(themeId));
		return responseService.getListResult(dto);
	}
	
	
	
	//?????? ?????? ?????? ??????
	@GetMapping("/slots/date") // ??????, ?????? ??????
	public SingleResult<LocalDate> getSlotOpenedDate(
			@RequestParam(value = "branchId")String branchId,
			@RequestParam(value = "themeId")String themeId){
		LocalDate slotTime = slotService.????????????????????????(Integer.parseInt(branchId),Integer.parseInt(themeId));

		return responseService.getSingleResult(slotTime);
	}
	
	//minDate??? maxDate ????????? ??? ?????? ??????.
	@GetMapping("/slots/date/disabled")	// ??????, ?????? ??????
	public ListResult<LocalDate> getDisableDate(
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			@RequestParam(value = "min")LocalDate minDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			@RequestParam(value = "max")LocalDate maxDate){
		List<LocalDate> list = slotService.???????????????(minDate, maxDate);
		return responseService.getListResult(list);
	}
	
	
	
	
	//?????? ?????? ??????
	//?????? ?????? ?????? ??????
	/*@GetMapping("/slots")
	public ListResult<Slot> findAllSlot(Pageable pageable
			//, @Authentication PrincipalDetail principal
			){
		//	if(pricipal.getRole.equals("ROLE_ADMIN"){
		//		Page<Slot> list = slotService.??????????????????(pageable);
		// }else{
		//		Page<Slot> list = slotService.????????????????????????(pageable);
		//}
		Page<Slot> list = slotService.??????????????????(pageable);
		return responseService.getPageListResult(list);
	}
	*/
	
}
