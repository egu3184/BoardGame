package com.egu.boot.BoardGame.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Theme;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.service.ThemeService;
import com.egu.boot.BoardGame.service.api.ResponseService;

@RestController
public class ThemeApiController {

	@Autowired
	private ThemeService themeService;
	
	@Autowired
	ResponseService responseService;
	
	//테마 등록
	@PostMapping("/themes")
	public CommonResult saveTheme(@RequestBody Theme theme) {
		themeService.테마저장(theme);
		return responseService.getSuccessResult();
	}
	
	//테마 수정
	@PutMapping("/themes/{id}")
	public CommonResult editTheme(@PathVariable int id, @RequestBody Theme requestTheme) {
		themeService.테마수정(id, requestTheme);
		return responseService.getSuccessResult();
	}
	
	//테마 삭제
	@DeleteMapping("/themes/{id}")
	public CommonResult deleteTheme(@PathVariable int id) {
		themeService.테마삭제(id);
		return responseService.getSuccessResult();
	}
	
	//테마 리스트 조회
	@GetMapping("/theme")
	public CommonResult selectThemeList(
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<Theme> list =  themeService.테마리스트(pageable);
		return responseService.getPageListResult(list);
	}
	
	//테마 조회
	@GetMapping("/theme/{id}")
	public CommonResult selectTheme(@PathVariable int id) {
		Theme theme = themeService.테마조회(id);
		return responseService.getSingleResult(theme);
	}
	
	
	
}
