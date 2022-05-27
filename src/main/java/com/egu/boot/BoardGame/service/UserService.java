package com.egu.boot.BoardGame.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.RoleType;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.UserDto.UserRequestDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserResponseDto;
import com.egu.boot.BoardGame.repository.QUserRepository;
import com.egu.boot.BoardGame.repository.QUserRepositoryImpl;
import com.egu.boot.BoardGame.repository.UserRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final QUserRepositoryImpl quserRepository;

	@Transactional
	public User 회원가입(UserRequestDto requestDto) {
		//DTO의 모든 필드 값이 null이 아닌지 체크
		for(Field field : requestDto.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				if(field.get(requestDto) == null) throw new CustomException(ErrorCode.USERINFO_NOT_ENOUGH);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new CustomException(ErrorCode.BAD_REQUEST);
			}
		}
		//마지막으로 다시 한번 중복 체크 
		User user= quserRepository.findUserByUserInfo(requestDto);
		if(!Objects.isNull(user)) throw new CustomException(ErrorCode.USERINFO_ALREADY_USED);
		
		//체크 후 가입 처리
		String rawPassword = requestDto.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		user = User.builder()
				.userId(requestDto.getUserId())
				.password(encPassword)
				.provider("Application")
				.isEnabled(true)
				.nickname(requestDto.getNickname())
				.roles(Collections.singletonList("ROLE_USER"))
				.createDate(LocalDateTime.now())
				.phoneNumber(requestDto.getPhoneNum())
				.privacyAgree(requestDto.getPrivacyAgree())
				.prAgree(requestDto.getPrAgree())
				.build();
		return userRepository.save(user);
	}

	@Transactional
	public UserResponseDto 회원찾기(String userId) {
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
	public UserResponseDto 회원정보수정(UserRequestDto requestDto) {	 
		// (1) 시큐리티 컨텍스트에서 유저 정보 가져오기
		User user = new User();
		try {
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(user == null) throw new CustomException(ErrorCode.BAD_REQUEST);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.BAD_REQUEST);
		}	
		if(!ObjectUtils.isEmpty(requestDto.getPassword())) {
			//(2-1)비밀번호 변경시 - currentPw와 로그인 회원 pw 비교
			if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
				throw new CustomException(ErrorCode.INVALID_PASSWORD);	
		}else {
			//(2-2)비밀번호 제외 회원 정보 변경시 - 마지막으로 다시 한번 중복 체크 
			if(!Objects.isNull(quserRepository.findUserByUserInfo(requestDto))) 
				throw new CustomException(ErrorCode.USERINFO_ALREADY_USED);
		}
		//(3)유저 정보 수정
		long updateCount = quserRepository.modifyUserInfo(requestDto, user.getId());
		if(updateCount > 0) {
			return new UserResponseDto(userRepository.findById(user.getId()).orElseThrow(()->{
				throw new CustomException(ErrorCode.USERINFO_CHANGE_FAILED);
			}));
		}else {
				throw new CustomException(ErrorCode.USERINFO_CHANGE_FAILED);
		}
	}
	
	@Transactional
	public void 회원탈퇴(String rawPassword) {
		//시큐리티 컨텍스트에서 유저 정보 가져오기
		User user = (User) SecurityContextHolder.getContext().getAuthentication();
		//비밀번호 재입력 비밀번호 체크
		if(!passwordEncoder.matches(rawPassword, user.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		}
		user.setDeactivatedDate(LocalDateTime.now());
		user.setEnabled(false);
	}
	

	@Transactional
	public UserResponseDto 로그인(String userId, String pw) {
		// (1) 회원 조회 
		User user = userRepository.findByUserIdAndProvider(userId, "Application").orElseThrow(()->{
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		});
		//(2-1) 회원 상태 체크
		if(user.isEnabled() == false) throw new CustomException(ErrorCode.USER_DISABLED);
		//(2-2) 패스워드 검증 전 체크
		if(pw == null || pw.equals("")) throw new CustomException(ErrorCode.USER_NOT_FOUND);
		//(2-3) 패스워드 검증
		if(!passwordEncoder.matches(pw, user.getPassword())) throw new CustomException(ErrorCode.USER_NOT_FOUND);
		// (3) 토큰 생성
		String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), user.getRoles());
		String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()));
		//(4) 토큰 반환	
		return new UserResponseDto(accessToken, refreshToken);
	}
	
	@Transactional
	public User 회원정보로찾기(UserRequestDto requestDto) {
		User user= quserRepository.findUserByUserInfo(requestDto);
		return user;
	}

	
	
}
