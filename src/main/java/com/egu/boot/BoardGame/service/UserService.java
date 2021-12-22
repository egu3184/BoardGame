package com.egu.boot.BoardGame.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.handler.CustomUserNotFoundException;
import com.egu.boot.BoardGame.model.RoleType;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public User 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		user.setRegisterd(true);
		user.setCreateDate(LocalDateTime.now());
		
		return userRepository.save(user);
	}

	@Transactional
	public User 회원찾기(int id) {
		return userRepository.findById(id).orElseGet(null);
		//return userRepository.findById(id)
		//		.orElseThrow(CustomUserNotFoundException::new);
	}

	@Transactional
	public Page<User> 회원리스트찾기(Pageable pageable) {
		Page<User> list = userRepository.findAll(pageable);
		return list;
	}

	@Transactional
	public void 회원수정(User requestUser) {
		
		
	}
	
	
	
}
