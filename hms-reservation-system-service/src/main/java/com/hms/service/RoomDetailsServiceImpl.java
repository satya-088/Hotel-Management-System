package com.hms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.entity.Reservation;
import com.hms.entity.RoomDetails;
import com.hms.entity.UserCheckRoomDetails;
import com.hms.exception.CustomException;
import com.hms.repo.ReservationRepo;
import com.hms.repo.RoomDetailsRepo;

@Service
public class RoomDetailsServiceImpl implements RoomDetailsService {
	
	@Autowired
	private RoomDetailsRepo detailsRepo;
	
	@Autowired
	private ReservationRepo reserveRepo;
	
	
	private Logger log = LoggerFactory.getLogger(RoomDetailsServiceImpl.class);

	@Override
	public List<RoomDetails> addAllRoomDetails(List<RoomDetails> rooms) {
		
		LocalDate currentDate = LocalDate.now();
		
		LocalDate yesterdayDate = currentDate.minusDays(5);
		
		
		for(RoomDetails ls : rooms) {
			
			ls.setCheckIn(yesterdayDate);
			ls.setCheckOut(yesterdayDate);
			ls.setAmount(ls.getRoomType().getAmount());
			
		}
		
		
		List<RoomDetails> saveAll = detailsRepo.saveAll(rooms);
		
		return saveAll;
		
		
	}




	@Override
	public List<UserCheckRoomDetails> checkListOfRoomDetails() throws CustomException {
		
		List<UserCheckRoomDetails> allRoomDetailsDTO = detailsRepo.findAllRoomDetailsDTO();
		List<Reservation> all = reserveRepo.findAll();	
		
		
		List<UserCheckRoomDetails> rem = new ArrayList<>();
		
		for(UserCheckRoomDetails ls : allRoomDetailsDTO) {
			rem.add(ls);
		}
		
		for(Reservation ls : all) {
			
			for(UserCheckRoomDetails l : allRoomDetailsDTO) {
				
				if(ls.getRoomNum().equals(l.getRoomNo())) {
					
					rem.remove(l);
				
				}
			}	
			
		}
		
		if(!(rem.isEmpty())) {
			
			log.info("Rooms are available");
			return rem;
		}
		else {
			
			throw new CustomException("Rooms are not Available");
			
		}

	}

	@Override
	public List<UserCheckRoomDetails> listOfRoomDetailsByFloorNo(int num) throws CustomException {
		
		List<UserCheckRoomDetails> allRoomDetailsDTO = detailsRepo.findAllRoomDetailsDTO();
		
		List<UserCheckRoomDetails> list = new ArrayList<>();
		
		for(UserCheckRoomDetails ls : allRoomDetailsDTO) {
			int n1 = num*100;
			int n2 = (num+1)*100;
			int roomNo = Integer.valueOf(ls.getRoomNo().substring(1));
			if(roomNo>n1 && roomNo<n2) {
				list.add(ls);
				
			}
		}
		
		if(list.isEmpty()) {
			
			throw new CustomException("Floor number not found by "+num);
		}else {
			
			return list;
		}
		
		
		
	}

	


	
	
	

}
