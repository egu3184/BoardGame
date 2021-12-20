package com.egu.boot.BoardGame.model.api;

import lombok.Data;

@Data
public class SingleResult<T> extends CommonResult {
 
	private T data;
}
