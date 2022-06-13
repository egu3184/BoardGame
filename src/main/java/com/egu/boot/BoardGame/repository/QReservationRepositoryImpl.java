package com.egu.boot.BoardGame.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationRequestDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserRequestDto;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAUpdateClause;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QReservationRepositoryImpl implements QReservationRepository {
	
	private final EntityManager entityManager;

	@Override
	public long updateReservation(ReservationRequestDto requestDto, User user) {
		
		long result = updateClause(requestDto) 
									.where(reservation.id.eq(requestDto.getReservationId())
													,eqUser(user)
													,eqPhoneNum(requestDto.getPhoneNum())
											)
									.execute();
		return result;
	}
	
	private UpdateClause<JPAUpdateClause> updateClause(ReservationRequestDto requestDto){
		UpdateClause<JPAUpdateClause> updateClause = new JPAUpdateClause(entityManager, reservation);
		Optional.ofNullable(requestDto.getReservationStatus())
			.ifPresent(reservationStatus -> updateClause.set(reservation.reservationStatus, requestDto.getReservationStatus()));
		Optional.ofNullable(requestDto.getBookerName())
			.ifPresent(bookerName -> updateClause.set(reservation.bookerName, requestDto.getBookerName()));
		Optional.ofNullable(requestDto.getPhoneNum())
			.ifPresent(phoneNum -> updateClause.set(reservation.phoneNumber, requestDto.getChangePhoneNum()));
		//... 이후 필요시 추가
		//만약 set절이 모두 null이면?
		if(updateClause.isEmpty()) {
			throw new CustomException(ErrorCode.REQUEST_BODY_MISSING);
		}
		return updateClause;
	}
	
	private BooleanExpression eqUser(User user) {
		if(user == null) { return null; }
		return reservation.user.eq(user);
	}
	private BooleanExpression eqPhoneNum(String phoneNum) {
		if(phoneNum == null) { return null; }
		return reservation.phoneNumber.eq(phoneNum);
	}

}
