package com.egu.boot.BoardGame.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
	GUEST,
	USER, 
	ADMIN;

	
}
