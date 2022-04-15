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

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
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
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

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

	

	@Transactional
	public UserResponseDto 로그인(String userId, String pw) {
		
		User user = userRepository.findByUserId(userId).orElseThrow(()->{
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		});
		System.out.println(user.getRoles());
		//패스워드 검증
		if(!passwordEncoder.matches(pw, user.getPassword())) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
		//토큰 생성
		String accessToken = jwtTokenProvider.createAccessToken(user.getUserId(), user.getRoles());
		String refreshToken = jwtTokenProvider.createRefreshToken();
		//refreshToken db에 저장
		saveRefreshToken(user, refreshToken);
		//토큰 반환	
		return new UserResponseDto(accessToken, refreshToken);
	}
	
	@Transactional
	public void saveRefreshToken(User user, String refreshToken) {
		user.setRefreshToken(refreshToken);
	}
	
	
}
