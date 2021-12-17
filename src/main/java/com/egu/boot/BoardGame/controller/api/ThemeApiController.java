package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.Theme;
import com.egu.boot.BoardGame.service.ThemeService;

@RestController
public class ThemeApiController {

	@Autowired
	private ThemeService themeService;
	
	@PostMapping("/theme/save")
	public String saveTheme(@RequestBody Theme theme) {
		
		themeService.테마저장(theme);
	
		return "테마 저장 완료";
	}
	
	
	
	
	
}
