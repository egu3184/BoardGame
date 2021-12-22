package com.egu.boot.BoardGame.service.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.ListResult;
import com.egu.boot.BoardGame.model.api.SingleResult;

import lombok.Getter;

@Service
public class ResponseService {

	@Getter
	public enum ResponseEnum {
		SUCCESS(0, "성공하였습니다."), FAIL(-1, "실패하였습니다.");

		private int code;
		private String msg;

		private ResponseEnum(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
	}

	// 결과 모델에 api 요청 성공 데이터를 셋팅해주는 api
	public void setSuccessResult(CommonResult result) {
		result.setSuccess(true);
		result.setCode(ResponseEnum.SUCCESS.getCode());
		result.setMassage(ResponseEnum.SUCCESS.getMsg());
	}

	// 성공 결과를 리턴하는 메소드
	public CommonResult getSuccessResult() {
		CommonResult result = new CommonResult();
		setSuccessResult(result);
		return result;
	}

	// 실패 결과를 리턴하는 메소드
	public CommonResult getFailResult(int code, String msg) {
		CommonResult result = new CommonResult();
		result.setSuccess(false);
		result.setCode(code);
		result.setMassage(msg);
		return result;
	}

	// 단일 결과 처리하는 메소드
	public <T> SingleResult<T> getSingleResult(T data) {
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		setSuccessResult(result);
		return result;
	}

	// 복수 결과 처리하는 메소드 - 리스트
	public <T> ListResult<T> getListResult(List<T> data) {
		ListResult<T> result = new ListResult<>();
		result.setList(data);
		setSuccessResult(result);
		return result;
	}

	// 복수 결과 처리하는 메소드 - 페이지
	public <T> ListResult<T> getPageListResult(Page<T> data) {
		ListResult<T> result = new ListResult<>();
		result.setPageList(data);
		setSuccessResult(result);
		return result;
	}

}
