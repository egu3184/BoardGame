package com.egu.boot.BoardGame.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.UserDto.UserRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;


import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QUserRepositoryImpl implements QUserRepository{
	
	private final JPAQueryFactory jpaQueryFactory;
	private final PasswordEncoder passwordEncoder;
	private final EntityManager entitymanager;
	
	@Override
	public User findUserByUserInfo(UserRequestDto requestDto) {
		BooleanBuilder builder = getBooleanBuilder(requestDto);
		try {
			User result = jpaQueryFactory
										.selectFrom(user)
										.where(builder)
										.fetchFirst();
			return result;
		} catch (NullPointerException e) {
			//결과값이 없을 때
			return null;
		}				
	}
	
	@Override
	public long modifyUserInfo(UserRequestDto requestDto, int id) {
		long excute =
			updateClause(requestDto)
			.where(user.id.eq(id))
			.execute();
		return excute;
	}
	
	//Dynamic Set을 사용해야할 때 - UpdateClause
	private UpdateClause<JPAUpdateClause> updateClause(UserRequestDto requestDto ){
		UpdateClause<JPAUpdateClause> updateBuilder = new JPAUpdateClause(entitymanager, user);
		Optional.ofNullable(requestDto.getNickname())
			.ifPresent(nickaname -> updateBuilder.set(user.nickname, requestDto.getNickname()));
		if(StringUtils.hasText(requestDto.getPhoneNum())) {
			updateBuilder.set(user.phoneNumber, requestDto.getPhoneNum());
		}
		if(!ObjectUtils.isEmpty(requestDto.getPrAgree())) {
			updateBuilder.set(user.prAgree, requestDto.getPrAgree());
		}
		if(!ObjectUtils.isEmpty(requestDto.getNewPassword())) {
			String newPw = passwordEncoder.encode(requestDto.getNewPassword());
			updateBuilder.set(user.password, newPw);
		}
		return updateBuilder;
	}
	
	//Dynamic Where절을 사용해야할 때 - BooleanBuilder
	private BooleanBuilder getBooleanBuilder(UserRequestDto requestDto) {
		BooleanBuilder builder = new BooleanBuilder();
		Optional.ofNullable(requestDto.getUserId())
			.ifPresent(userId -> builder.or(user.userId.eq(requestDto.getUserId())));
		Optional.ofNullable(requestDto.getNickname())
			.ifPresent(nickname -> builder.or(user.nickname.eq(requestDto.getNickname())));
		Optional.ofNullable(requestDto.getPhoneNum())
			.ifPresent(phoneNum -> builder.or(user.phoneNumber.eq(requestDto.getPhoneNum())));
		//requestDto가 모두 null이라 where절이 없을 경우
		if(builder.hashCode() == 0) {
			throw new CustomException(ErrorCode.USERINFO_NOT_ENOUGH);
		}
		return builder;
	}
	
	private BooleanExpression eqNickname(String nickname) {
		System.out.println(nickname);
		if(nickname == null) { return null; }
		return user.nickname.eq(nickname);
	}
	
	private BooleanExpression eqPhoneNum(String phoneNum) {
		System.out.println(phoneNum);
		if(phoneNum == null) {return null;}
		return user.phoneNumber.eq(phoneNum);
	}
	
	private BooleanExpression eqUserId(String userId) {
		System.out.println(userId);
		if(userId == null) {return null;}
		return user.userId.eq(userId);
	}

	

	
	
}
