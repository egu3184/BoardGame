package com.egu.boot.BoardGame.repository;

import java.util.List;

import com.egu.boot.BoardGame.model.QUser;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.UserDto.UserRequestDto;

public interface QUserRepository {
		
	QUser user =  QUser.user;
	
	User findUserByUserInfo(UserRequestDto requestDto);
	
}
