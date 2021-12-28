package com.egu.boot.BoardGame.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.Reservation;
import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.ReservationRequestDto;
import com.egu.boot.BoardGame.repository.FindReservationRepository;
import com.egu.boot.BoardGame.repository.ReservationRepository;
import com.egu.boot.BoardGame.repository.SlotRepository;
import com.egu.boot.BoardGame.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {


	private final ReservationRepository reservationRepository;
	private final UserRepository userRepository;
	private final SlotRepository slotRepository;
	private final FindReservationRepository findReservationRepository;

	@Transactional
	public Reservation 예약등록(ReservationRequestDto reservationRequestDto) {
		User user = null;
		if(reservationRequestDto.getUserId() != null) {
			 user = userRepository.findById(reservationRequestDto.getUserId()).orElseThrow(() -> {
					throw new CustomException(ErrorCode.USER_NOT_FOUND);
			 });
		}
		Slot slot = slotRepository.findById(reservationRequestDto.getSlotId()).orElseThrow(() -> {
			throw new CustomException(ErrorCode.SLOT_NOT_FOUND);
		});
		if (slot.isReserved() == true) {
			throw new IllegalArgumentException("이미 예약된 슬롯입니다.");
		} else {
			Reservation reservation = Reservation.builder()
					.reservationTime(LocalDateTime.now())
					.persons(reservationRequestDto.getPersons())
					.payment(reservationRequestDto.getPayment())
					.isPaid(false)
					.user(user)
					.bookerName(reservationRequestDto.getBookerName())
					.phoneNumber(reservationRequestDto.getPhoneNumber())
					.email(reservationRequestDto.getEmail())
					.slot(slot).build();
			slot.setReserved(true); // 슬롯 예약됨으로 변경	
			return reservationRepository.save(reservation);
		}
	}

	@Transactional
	public Reservation 예약조회(int id) {
		Reservation reservation = reservationRepository.findById(id).orElseThrow(()->{
				throw new CustomException(ErrorCode.RESERVATION_NOT_FOUND);
			});
		return reservation;
	}

	@Transactional
	public List<Reservation> 예약검색조회(String bookerName, String phoneNumber, Integer id) {
		List<Reservation> list =  findReservationRepository.searchReservation(bookerName, phoneNumber, id);
		return list;
	}
	
	
	
	
}
