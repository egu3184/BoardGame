package com.egu.boot.BoardGame.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername의 userId"+ userId);
		User user = userRepository.findByUserId(userId).orElseThrow(()->{
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		});
		System.out.println("loadUserByUsername의 user"+user.getUserId());
		
		User userd = userRepository.findByUserId(userId).orElseThrow(()->{
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		});
		return userd;
	}

}
