package com.egu.boot.BoardGame.service.api;

import java.util.List;

import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.ListResult;
import com.egu.boot.BoardGame.model.api.SingleResult;

import lombok.Getter;

public class ResponseService {

	@Getter
	public enum ResponseEnum{
		SUCCESS(0, "성공하였습니다."),
		FAIL(-1, "실패하였습니다.");
		
		private int code;
		private String msg;
		
		private ResponseEnum(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
	}
	
	//성공 처리 메소드
	private void getSuccessResult(CommonResult result) {
		result.setSuccess(true);
		result.setCode(ResponseEnum.SUCCESS.getCode());
		result.setMassage(ResponseEnum.SUCCESS.getMsg());
	}
	
	//실패 처리 메소드
	private void getFailResult(CommonResult result) {
		result.setSuccess(false);
		result.setCode(ResponseEnum.FAIL.getCode());
		result.setMassage(ResponseEnum.FAIL.getMsg());
	}
	
	
	//단일 결과 처리하는 메소드
	public <T> SingleResult<T> getSingleResult(T data){
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		getSuccessResult(result);
	
		return result;
	}
	
	//복수 결과 처리하는 메소드
	public <T> ListResult<T> getListResult(List<T> data){
		ListResult<T> result = new ListResult<>();
		result.setList(data);
		getSuccessResult(result);
		return result;
	}
	
	
	
}
