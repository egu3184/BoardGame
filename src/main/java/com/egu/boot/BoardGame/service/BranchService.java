package com.egu.boot.BoardGame.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.model.dto.BranchDto;
import com.egu.boot.BoardGame.model.dto.BranchDto.BranchResponseDto;
import com.egu.boot.BoardGame.model.dto.ResponseDto;
import com.egu.boot.BoardGame.repository.BranchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchService {

	private final BranchRepository branchRepository;
	
	@Transactional
	public List<BranchResponseDto> 지점리스트조회(){
		List<Branch> branches= branchRepository.findAll();
		List<BranchResponseDto> dtoList = new ArrayList<BranchResponseDto>();
		for(Branch branch : branches) {
			dtoList.add(new BranchDto.BranchResponseDto(branch));
		}
		return dtoList;
	}
	
}
