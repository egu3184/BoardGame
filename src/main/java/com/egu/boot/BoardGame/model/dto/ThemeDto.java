package com.egu.boot.BoardGame.model.dto;

import com.egu.boot.BoardGame.model.Theme;

import lombok.Getter;

public class ThemeDto {
	
	@Getter
	public static class ThemeResponseDto{
		private Integer themeId;
		private String themeName;
		private String genre;
		private String description;
		private String minimumCapacity;
		private String maximumCapacity;
		private String playTime;
		private String admissionFee;
		private String difficulty;
		
		public ThemeResponseDto(Theme theme){
			this.themeId = theme.getId();
			this.themeName = theme.getThemeName();
			this.genre = theme.getGenre();
			this.description = theme.getDescription();
			this.minimumCapacity = theme.getMinimumCapacity();
			this.maximumCapacity = theme.getMaximumCapacity();
			this.playTime = theme.getPlayTime();
			this.admissionFee = theme.getAdmissionFee();
			this.difficulty = theme.getDifficulty();
		}
	}

}
