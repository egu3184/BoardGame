package com.egu.boot.BoardGame.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egu.boot.BoardGame.model.BankAccount;
import com.egu.boot.BoardGame.model.dto.BankAccountDto;import com.egu.boot.BoardGame.model.dto.BranchDto;
import com.egu.boot.BoardGame.model.dto.BankAccountDto.BankAccountResponseDto;
import com.egu.boot.BoardGame.repository.BankAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountService {
	
	private final BankAccountRepository baRepository;

	@Transactional
	public List<BankAccountResponseDto> 계좌리스트조회(int branchId) {
		
		List<BankAccount> list = baRepository.findAllBybranchId(branchId);
		List<BankAccountResponseDto> dtoList = new ArrayList<BankAccountResponseDto>(); 
		for(BankAccount ba : list) {
			System.out.println(ba.getId());
			dtoList.add(new BankAccountDto.BankAccountResponseDto(ba));
		}
		
		
		return dtoList;
	}


}
