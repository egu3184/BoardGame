package com.egu.boot.BoardGame.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.repository.BranchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchService {

	private final BranchRepository branchRepository;
	
	@Transactional
	public List<Branch> 지점조회(){
		List<Branch> list= branchRepository.findAll();
		return list;
	}
	
}
