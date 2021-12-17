package com.egu.boot.BoardGame.service.api;

import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.SingleResult;

import lombok.Getter;

public class ResponseService {

	@Getter
	public enum CommonResponse{
		SUCCESS(0, "성공하였습니다."),
		FAIL(-1, "실패하였습니다.");
		
		private int code;
		private String msg;
		
		private CommonResponse(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
	}
	
	//성공 처리 메서드
	private void setSuccessResult(CommonResult result) {
		result.setSuccess(true);
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMassage(CommonResponse.SUCCESS.getMsg());
	}
	
	
	public <T> SingleResult<T> getSingleResult(T data){
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		setSuccessResult(result);
	
		return result;
	}
	
	
	
}
