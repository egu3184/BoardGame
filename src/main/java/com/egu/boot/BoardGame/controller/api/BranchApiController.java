package com.egu.boot.BoardGame.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.repository.BranchRepository;
import com.egu.boot.BoardGame.service.BranchService;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BranchApiController {

	private final BranchService branchService;
	
	private final ResponseService responseService;
	
	@GetMapping("/branches")
	public CommonResult getBranchList() {
		
		List<Branch> list= branchService.지점조회();
		
		return responseService.getListResult(list) ;
	}
	
	
}
