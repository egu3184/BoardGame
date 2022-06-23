package com.egu.boot.BoardGame.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.api.ListResult;
import com.egu.boot.BoardGame.model.dto.BankAccountDto;
import com.egu.boot.BoardGame.model.dto.BankAccountDto.BankAccountResponseDto;
import com.egu.boot.BoardGame.service.BankAccountService;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BankAccountApiController {

	private final BankAccountService baService;
	private final ResponseService responseService;
	
	@GetMapping("/bank")
	public ListResult<BankAccountResponseDto> getBAList(@RequestParam(value="branchId")int branchId){
		
		List<BankAccountResponseDto> list = baService.계좌리스트조회(branchId);
		
		return responseService.getListResult(list);
	}
	
	
}
