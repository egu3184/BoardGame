package com.egu.boot.BoardGame.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.RoleType;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.UserDto.UserRequestDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserResponseDto;
import com.egu.boot.BoardGame.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final BCryptPasswordEncoder encoder;
	
	@Transactional
	public User 회원가입(UserRequestDto dto) {
		String rawPassword = dto.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		User user = User.builder()
				.userId(dto.getUserId())
				.password(encPassword)
				.username(dto.getUsername())
				.roles(Collections.singletonList("ROLE_USER"))
				.createDate(LocalDateTime.now())
				.phoneNumber(dto.getPhoneNumber())
				.build();
		
		return userRepository.save(user);
	}

	@Transactional
	public UserResponseDto 회원찾기(String userId) {
//		System.out.println("여기까지 들어온 건가?"+userId);
		User user = userRepository.findByUserId(userId).orElseThrow(()->{
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		});
		return  new UserResponseDto(user);
	}

	@Transactional
	public Page<User> 회원리스트찾기(Pageable pageable) {
		Page<User> list = userRepository.findAll(pageable);
		return list;
	}

	@Transactional
	public void 회원수정(User requestUser) {
		
		
	}

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
		//UserDetails를 리턴하는 과정에서 username이 UserDetails의 username으로 들어가는듯 싶다.
		//이걸 어떻게 해결할까?
		//userDetail의 직접 set한다 -> setter가 없음.
		//컬럼명을 바꾼다 -> 이게 현실적일듯
		//아니지! user도 UserDetails잖아! (상속)
		System.out.println("loadUserByUsername의 userd"+userd.getUserId());
		return userd;
	}

	@Transactional
	public User 로그인(String userId, String pw) {
		User user = userRepository.findByUserId(userId).orElseThrow(()->{
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		});
		if(!passwordEncoder.matches(pw, user.getPassword())) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
		return user;
	}
	
	
	
	
}
