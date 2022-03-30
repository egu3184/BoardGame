package com.egu.boot.BoardGame.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.ListResult;
import com.egu.boot.BoardGame.model.dto.BranchDto.BranchResponseDto;
import com.egu.boot.BoardGame.model.dto.BranchDto;
import com.egu.boot.BoardGame.model.dto.ResponseDto;
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
	public ListResult<BranchResponseDto> getBranchList() {
		List<BranchResponseDto> dto = branchService.지점리스트조회();
		return responseService.getListResult(dto);
	}
	
	
}
