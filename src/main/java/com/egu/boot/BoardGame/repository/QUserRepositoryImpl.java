package com.egu.boot.BoardGame.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.UserDto.UserRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QUserRepositoryImpl implements QUserRepository{
	
	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public User findUserByUserInfo(UserRequestDto requestDto) {
		//BooleanBuilder는 querydsl에서 동적인 where절을 만들어주는 클래스이다. 
		BooleanBuilder builder = getBooleanBuilder(requestDto);
		try {
			User result = jpaQueryFactory
										.selectFrom(user)
										.where(builder)
										.fetchFirst();	//모두 unique컬럼이기 때문에 1개 이상 없음.
			return result;
		} catch (NullPointerException e) {
			//결과값이 없을 때
			return null;
		}				
	}
	
	private BooleanBuilder getBooleanBuilder(UserRequestDto requestDto) {
		BooleanBuilder builder = new BooleanBuilder();
		Optional.ofNullable(requestDto.getUserId())
			.ifPresent(userId -> builder.or(user.userId.eq(requestDto.getUserId())));
		Optional.ofNullable(requestDto.getNickname())
			.ifPresent(nickname -> builder.or(user.nickname.eq(requestDto.getNickname())));
		Optional.ofNullable(requestDto.getPhoneNum())
			.ifPresent(phoneNum -> builder.or(user.phoneNumber.eq(requestDto.getPhoneNum())));
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
