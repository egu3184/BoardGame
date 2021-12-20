package com.egu.boot.BoardGame.model.api;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class ListResult<T> extends CommonResult {
 
	private List<T> list;
	
	private Page<T> pageList;
}
