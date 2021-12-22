package com.egu.boot.BoardGame.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.model.Reservation;
import com.egu.boot.BoardGame.model.ReservationRequestDto;
import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.repository.ReservationRepository;
import com.egu.boot.BoardGame.repository.SlotRepository;
import com.egu.boot.BoardGame.repository.UserRepository;

@Service
public class reservationService {

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	SlotRepository slotRepository;

	@Transactional
	public Reservation 예약등록(ReservationRequestDto reservationRequestDto) {
		User user = null;
		String phoneNumber = null;
		String guestName = null;

		if (reservationRequestDto.getUserId() == null) { // 비회원시
			guestName = reservationRequestDto.getGuestName();
			phoneNumber = reservationRequestDto.getPhoneNumber();
		} else { // 회원시
			user = userRepository.findById(reservationRequestDto.getUserId()).orElseThrow(() -> {
				return new IllegalArgumentException("등록된 유저가 아닙니다.");
			});
			guestName = user.getUsername();
			phoneNumber = user.getPhoneNumber();
		}
		Slot slot = slotRepository.findById(reservationRequestDto.getSlotId()).orElseThrow(() -> {
			throw new IllegalArgumentException("존재하지 않거나 예약 가능한 슬롯이 아닙니다.");
		});
		if (slot.isReserved() == true) {
			throw new IllegalArgumentException("이미 예약된 슬롯입니다.");
		}else {
			Reservation reservation = Reservation.builder().reservationTime(LocalDateTime.now())
					.persons(reservationRequestDto.getPersons()).payment(reservationRequestDto.getPayment()).isPaid(false)
					.user(user).guestName(guestName).phoneNumber(phoneNumber).slot(slot).build();
			slot.setReserved(true); // 슬롯 예약됨으로 변경
			return reservationRepository.save(reservation);
		}	

	}

}
