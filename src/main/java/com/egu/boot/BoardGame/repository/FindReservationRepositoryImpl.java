package com.egu.boot.BoardGame.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.egu.boot.BoardGame.model.Reservation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FindReservationRepositoryImpl implements FindReservationRepository {

	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<Reservation> searchReservation(String bookerName, String phoneNumber) {
	
		return queryFactory
				.selectFrom(reservation)
				.where(eqBookerName(bookerName),
							eqPhoneNumber(phoneNumber))
				.fetch();
	}
	
	private BooleanExpression eqBookerName(String bookerName) {
		if(bookerName == null) return null;
		return reservation.bookerName.eq(bookerName);
	}
	
	private BooleanExpression eqPhoneNumber(String phoneNumber) {
		if(phoneNumber == null) return null;
		return reservation.phoneNumber.eq(phoneNumber);
	}

}
	
	
