package com.egu.boot.BoardGame.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.model.Payment;
import com.egu.boot.BoardGame.model.Reservation;
import com.egu.boot.BoardGame.model.Slot;
import com.egu.boot.BoardGame.model.Theme;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.dto.ReservationDto;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationRequestDto;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationResponseDto;
import com.egu.boot.BoardGame.repository.BranchRepository;
import com.egu.boot.BoardGame.repository.FindReservationRepository;
import com.egu.boot.BoardGame.repository.PaymentRepository;
import com.egu.boot.BoardGame.repository.QReservationRepository;
import com.egu.boot.BoardGame.repository.QReservationRepositoryImpl;
import com.egu.boot.BoardGame.repository.ReservationRepository;
import com.egu.boot.BoardGame.repository.SlotRepository;
import com.egu.boot.BoardGame.repository.ThemeRepository;
import com.egu.boot.BoardGame.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final EntityManager entityManager;
	private final ReservationRepository reservationRepository;
	private final SlotRepository slotRepository;
	private final BranchRepository branchRepository;
	private final ThemeRepository themeRepository;
	private final PaymentRepository paymentRepository;
	private final QReservationRepositoryImpl qreservationRepository;

	//????????????
	@Transactional
	public ReservationResponseDto ????????????(ReservationRequestDto reservationRequestDto) {
		Slot slot = slotRepository.findById(reservationRequestDto.getSlotId()).<CustomException>orElseThrow(() -> {
			throw new CustomException(ErrorCode.SLOT_NOT_FOUND);
		});
		LocalDateTime slotDateTime = LocalDateTime.of(slot.getSlotDate(), slot.getSlotTime());
		if(slotDateTime.isBefore(LocalDateTime.now())){
			throw new CustomException(ErrorCode.SLOT_FORBIDDEN);
		}
		if(slot.isReserved() == true) {	//?????? ?????????
			throw new CustomException(ErrorCode.SLOT_ALEADY_RESERVED);
		}
		if(slot.isOpened() == false || slot.isShowed() == false) {	//?????? ?????? ?????? ????????? ?????????
			throw new CustomException(ErrorCode.SLOT_FORBIDDEN);
		}
		Branch branch = branchRepository.findById(reservationRequestDto.getBranchId()).<CustomException>orElseThrow(()->{
			throw new CustomException(ErrorCode.BRANCH_NOT_FOUND);
		});
		Theme theme = themeRepository.findById(reservationRequestDto.getThemeId()).<CustomException>orElseThrow(()->{
			throw new CustomException(ErrorCode.THEME_NOT_FOUND);
		});
		Payment payment = paymentRepository.findById(reservationRequestDto.getPaymentId()).<CustomException>orElseThrow(()->{
			throw new CustomException(ErrorCode.PAYMENT_NOT_FOUND);
		});

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if(auth != null && auth.getPrincipal() instanceof User) {
			 user = (User) auth.getPrincipal();
		}
		Reservation reservation = new Reservation(reservationRequestDto);
		reservation.setBranch(branch);
		reservation.setPayment(payment);
		reservation.setSlot(slot);
		reservation.setTheme(theme);
		reservation.setReservationStatus(true);
		reservation.setUser(user);
		reservation.setReservationNumber(
				 makeReservationNumber(slot.getSlotDate(), theme.getId(), branch.getId(),  reservationRequestDto.getPhoneNum())
				);
		Reservation reserv = reservationRepository.save(reservation); 
		slot.setReserved(true); // ?????? ??????????????? ??????	
		return new ReservationResponseDto(reserv);
	}
	
	//???????????? ????????? ?????????
	public String makeReservationNumber(LocalDate date, int themeId, int branchId, String phoneNum ) {
		//?????? / ????????????id / ??????id / ??????id / ????????? ?????????2??? / ?????? 3???
		String phoneNumber = phoneNum.replaceAll("[^0-9]", "").substring(9);	//????????? 2??????
		int randomNumber = new Random().nextInt(999);
		return date.format(DateTimeFormatter.BASIC_ISO_DATE)+
												Integer.toString(themeId) + Integer.toString(branchId)+phoneNumber+randomNumber;
	}

	//????????? ????????????
	@Transactional
	public ReservationResponseDto ?????????????????????(String reservationNumber,String bookerName, String phoneNum) {
		Reservation reservation = 
				reservationRepository.findByReservationNumberAndBookerNameAndPhoneNumber(
						reservationNumber,bookerName, phoneNum).<CustomException>orElseThrow(()->{
				throw new CustomException(ErrorCode.RESERVATION_NOT_FOUND);
			});
		//????????? ??????????????? ????????? ?????? ?????? ?????? ???????????? ***??? ??????
		ReservationResponseDto responseDto = new ReservationResponseDto(reservation);
		responseDto.setBookerName(encryptBookerName(reservation.getBookerName()));
		responseDto.setPhoneNumber(encryptPhoneNumber(reservation.getPhoneNumber()));
		return responseDto;
	}
	
	//????????? ??????????????? ???*???
	private String encryptBookerName(String bookerName) {
		String[] list = bookerName.split("");
		list[1] = "*";
		return String.join("", list);
	}
	//????????? ??????????????? 010-****-1234
	private String encryptPhoneNumber(String phoneNumber) {
		String[] list = phoneNumber.split("-");
		String[] secoundNumber = list[1].split("");
		for(int i=0; i<secoundNumber.length; i++) {
			secoundNumber[i] = "*";
		}
		list[1] = String.join("", secoundNumber);
		return String.join("-", list);
	}
	
	//??????????????????
	@Transactional
	public Map<String, Object> ??????????????????(Pageable pageable) {
		List<ReservationResponseDto> dtoList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<Reservation> pageableList = reservationRepository.findByUserOrderByIdDesc(user, pageable);
			for(int i=0; i<pageableList.getContent().size(); i++) {
				dtoList.add(new ReservationResponseDto(pageableList.getContent().get(i)));
			}
			map.put("totalPages", pageableList.getTotalPages());
			map.put("list", dtoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//?????? & ????????? ?????? ??????
	@Transactional
	public long ????????????(ReservationRequestDto requestDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if(auth != null & auth.getPrincipal() instanceof User) {
			//?????????
			user = (User) auth.getPrincipal();
		}else {
			//???????????? - ???????????? ??????
			Reservation reserv = reservationRepository.getById(requestDto.getReservationId());
			if(reserv.getPhoneNumber() != requestDto.getCheckPhoneNum()) { return 0; }
		}
		entityManager.detach(user);
		long result = qreservationRepository.updateReservation(requestDto, user);
		entityManager.find(Reservation.class, user.getId());
		return result;
	}
	
	

}
