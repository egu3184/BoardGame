package com.egu.boot.BoardGame.model.dto;

import com.egu.boot.BoardGame.model.Branch;

import lombok.Getter;

public class BranchDto {
	
	@Getter
	public static class BranchResponseDto {
		private Integer branchId;
		private String branchName;
		private String adress;
		private String phoneNumber;
	
		public  BranchResponseDto(Branch branch) {
			this.branchId = branch.getId();
			this.branchName = branch.getBranchName();
			this.adress = branch.getAdress();
			this.phoneNumber = branch.getPhoneNumber();
		}
	}
	
	
}
