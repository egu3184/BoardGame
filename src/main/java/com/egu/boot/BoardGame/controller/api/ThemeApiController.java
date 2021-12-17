package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Theme;
import com.egu.boot.BoardGame.service.ThemeService;

@RestController
public class ThemeApiController {

	@Autowired
	private ThemeService themeService;
	
	//등록
	@PostMapping("/themes")
	public String saveTheme(@RequestBody Theme theme) {
		themeService.테마저장(theme);
		return "테마 저장 완료";
	}
	
	//수정
	@PutMapping("/themes/{id}")
	public String editTheme(@PathVariable int id, @RequestBody Theme requestTheme) {
		themeService.테마수정(id, requestTheme);
		return "테마 수정 완료";
	}
	
	//삭제
	@DeleteMapping("themes/{id}")
	public String deleteTheme(@PathVariable int id) {
		themeService.테마삭제(id);
		return "테마 삭제 완료";
	}
	
	//조회
	//조회는 모델을 던지나?
	
	
	
	
}
