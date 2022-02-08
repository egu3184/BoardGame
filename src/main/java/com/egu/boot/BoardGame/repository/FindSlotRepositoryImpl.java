package com.egu.boot.BoardGame.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.egu.boot.BoardGame.model.Slot;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FindSlotRepositoryImpl implements FindSlotRepository {
	
	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<Slot> searchSlot(Integer id, 
													LocalDate startTime, LocalDate endTime,
													Boolean isOpened, Boolean isReserved) {
		return queryFactory
				.selectFrom(slot)
				.where(
						eqId(id),
						btSlotTime(startTime, endTime),
						eqOpened(isOpened),
						eqReserved(isReserved)
						).fetch();
	}
	
	private BooleanExpression btSlotTime(LocalDate startTime, LocalDate endTime) {
		if(startTime == null && endTime == null) {
			return null;
		}
		return slot.slotDate.between(startTime, endTime);
	}
	
	private BooleanExpression eqId(Integer id) {
		if(id== null) return null;
		return slot.id.eq(id);
	}
	

	private BooleanExpression eqOpened(Boolean isOpened) {
		if(isOpened == null) return null;
		return slot.isOpened.eq(isOpened);
	}
	
	private BooleanExpression eqReserved(Boolean isReserved) {
		if(isReserved== null) return null;
		return slot.isReserved.eq(isReserved);
	}

}
