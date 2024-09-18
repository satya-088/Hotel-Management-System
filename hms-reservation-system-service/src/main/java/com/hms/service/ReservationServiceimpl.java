package com.hms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.hms.entity.Guest;
import com.hms.entity.Reservation;
import com.hms.entity.RoomDetails;
import com.hms.exception.CustomException;
import com.hms.repo.GuestRepository;
import com.hms.repo.ReservationRepo;
import com.hms.repo.RoomDetailsRepo;

@Service
public class ReservationServiceimpl implements ReservationService {
	
	@Autowired
	private ReservationRepo reservationRepo;
	
	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private SetRatesClient setRatesClient;
	
	@Autowired
	private RoomDetailsRepo roomDetailsRepo;
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	private Logger log = LoggerFactory.getLogger(ReservationServiceimpl.class);
	

	public ReservationServiceimpl(ReservationRepo reservationRepo, SetRatesClient setRatesClient) {
		super();
		this.reservationRepo = reservationRepo;
		this.setRatesClient = setRatesClient;
	}




	@Override
	public Reservation saveReservations(Reservation reservation) throws CustomException {
		
		
//		LocalDate date = LocalDate.now();
//		
//		List<Reservation> all = reservationRepo.findAll();
//		
//		for(Reservation ls : all) {
//			
//			if(roomNum.equals(ls.getRoomNum())) {
//				if(ls.getCheckOut().isBefore(date)) {
//					
//					reservationRepo.delete(ls);
//					setRatesClient.deleteFetchingDto(ls.getMemberCode());
//					
//				}
//				
//					
//			}
//		}
		
		String roomNum = reservation.getRoomNum();
		LocalDate date = LocalDate.now();
		List<Reservation> alls = reservationRepo.findAll();
		
			for(Reservation ls : alls) {
			
				if(roomNum.equals(ls.getRoomNum())) {
					if(ls.getCheckOut().isAfter(date)) {
					
						throw new CustomException("room already reserved");
					
					}
				
					
				}
			}
		
		
		
		
		

		Reservation saveGuestDetails = setRatesClient.saveGuestDetails(reservation);
		if(saveGuestDetails.getCheckIn().isBefore(saveGuestDetails.getCheckOut())) {
			
			Reservation save = reservationRepo.save(saveGuestDetails);
			
			log.info("Reservation data added successfuly");
			
			RoomDetails byRoomNo = roomDetailsRepo.findByRoomNo(save.getRoomNum());
			
			byRoomNo.setCheckIn(save.getCheckIn());
			byRoomNo.setCheckOut(save.getCheckOut());
			
			roomDetailsRepo.save(byRoomNo);
			
			
			Guest guest = new Guest();
			
			guest.setMemberCode(save.getMemberCode());
			guest.setName(save.getGuestName());
			guest.setEmail(save.getEmail());
			guest.setAddress(save.getEmail());
			guest.setPhoneNumber(save.getMobileNo());
			guest.setGender(save.getGender());
			
			guestRepository.save(guest);
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom("burjkhalifahoteldubai@gmail.com"); // give mail address of user
			mailMessage.setTo(save.getEmail());
			mailMessage.setSubject("HMS Reservation");
			mailMessage.setText("Your Room Reservation Succesfully by Id is "+save.getMemberCode()+" and room number is "+save.getRoomNum());

			javaMailSender.send(mailMessage);
			
			
			
			
			return save;
			
		}
		else {
			
			throw new CustomException("checkout must be after check in");
		}
		
		

		
	}




	@Override
	public Reservation getReservationDetailsById(int id) throws CustomException {
		
		Optional<Reservation> byId = reservationRepo.findById(id);
		
		if(byId.isPresent()) {
			
			Reservation reservation = byId.get();
			
			log.info("reservation details found by "+id);
			
			return reservation;
		}
		else {
			
			throw new CustomException("Your Reservation details not found by "+id);
		}
		
		
	}




	@Override
	public List<Reservation> listOfReservationsByDate() throws CustomException {
		
		LocalDate curentDate = LocalDate.now();
		
		
		Optional<List<Reservation>> byCheckOutAfter = reservationRepo.findByCheckOutAfter(curentDate);
		
		
		if(byCheckOutAfter.isPresent()) {
			
			log.info("Reservation data displayed by byCheckOutAfter Current Date");
			
			List<Reservation> list = byCheckOutAfter.get();
			
			return list;
		}
		else {
			
			throw new CustomException("Reservation data is empty");
		}
		
	}




	@Override
	public String cancelReservationById(int id) throws CustomException {
		
		
		
		Optional<Reservation> byId = reservationRepo.findById(id);
		
		if(byId.isPresent()) {
			Reservation reservation = byId.get();
			
//			RoomDetails byRoomNo = roomDetailsRepo.findByRoomNo(reservation.getRoomNum());
//			LocalDate currentDate = LocalDate.now();
//			
//			LocalDate fivedaysBackDate = currentDate.minusDays(5);
//			
//			byRoomNo.setCheckIn(fivedaysBackDate);
//			byRoomNo.setCheckOut(fivedaysBackDate);
//			roomDetailsRepo.save(byRoomNo);
			
			setRatesClient.deleteFetchingDto(id);
			reservationRepo.delete(reservation);
			
			log.info("Your Room reservation is Cancelled");
			return "Your Room reservation is Cancelled";
			
			
		}
		else {
			
			throw new CustomException("Your Reservation not found by "+id);
		}
	}
	
	
	

}
