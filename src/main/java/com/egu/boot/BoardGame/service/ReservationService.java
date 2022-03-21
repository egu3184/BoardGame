package com.egu.boot.BoardGame.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.model.Payment;
import com.egu.boot.BoardGame.model.Reservation;
import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.Theme;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.ReservationDto;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationRequestDto;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationResponseDto;
import com.egu.boot.BoardGame.repository.BranchRepository;
import com.egu.boot.BoardGame.repository.FindReservationRepository;
import com.egu.boot.BoardGame.repository.PaymentRepository;
import com.egu.boot.BoardGame.repository.ReservationRepository;
import com.egu.boot.BoardGame.repository.SlotRepository;
import com.egu.boot.BoardGame.repository.ThemeRepository;
import com.egu.boot.BoardGame.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {


	private final ReservationRepository reservationRepository;
	private final UserRepository userRepository;
	private final SlotRepository slotRepository;
	private final FindReservationRepository findReservationRepository;
	private final BranchRepository branchRepository;
	private final ThemeRepository themeRepository;
	private final PaymentRepository paymentRepository;

	@Transactional
	public Integer 예약등록(ReservationRequestDto reservationRequestDto) {
		Slot slot = slotRepository.findById(reservationRequestDto.getSlotId()).orElseThrow(() -> {
			throw new CustomException(ErrorCode.SLOT_NOT_FOUND);
		});
		if(slot.isReserved() == true) {	//중복 요청 처리
			throw new CustomException(ErrorCode.SLOT_ALEADY_RESERVED);
		}
		Branch branch = branchRepository.findById(reservationRequestDto.getBranchId()).orElseThrow(()->{
			throw new CustomException(ErrorCode.BRANCH_NOT_FOUND);
		});
		Theme theme = themeRepository.findById(reservationRequestDto.getThemeId()).orElseThrow(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
		});
		Payment payment = paymentRepository.findById(reservationRequestDto.getPaymentId()).orElseThrow(()->{
			throw new CustomException(ErrorCode.PAYMENT_NOT_FOUND);
		});
		Reservation reservation = new Reservation(reservationRequestDto);
		reservation.setBranch(branch);
		reservation.setPayment(payment);
		reservation.setSlot(slot);
		reservation.setTheme(theme);
		
		Reservation reserv = reservationRepository.save(reservation); 
		slot.setReserved(true); // 슬롯 예약됨으로 변경	
		
		return reserv.getId();
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
