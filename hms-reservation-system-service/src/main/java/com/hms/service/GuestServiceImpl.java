package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hms.entity.Guest;
import com.hms.exception.CustomException;
import com.hms.repo.GuestRepository;
@Component
public class GuestServiceImpl  implements GuestService{
  @Autowired
  GuestRepository guestRepository;
  
  
//	@Override
//	
//	public Guest addGuest(Guest guest) {
//
//		return guestRepository.save(guest);
//	}

	@Override
	public List<Guest> findAllGuestDetails() {
		
		return guestRepository.findAll();
	}

	@Override
	public Guest displayGuestDetails(int memberCode) throws CustomException{
		
		return guestRepository.findById(memberCode).get();
	}

	@Override
	public void deleteGuest(int memberCode) {
	guestRepository.deleteById(memberCode);
		
	}

}
