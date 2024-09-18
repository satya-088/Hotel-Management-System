package com.hms.service;

import java.util.List;

import com.hms.entity.Guest;
import com.hms.exception.CustomException;

public interface GuestService  {
	
   //Guest addGuest(Guest guest);
   List<Guest> findAllGuestDetails();
   Guest displayGuestDetails(int memberCode) throws CustomException;
   void deleteGuest(int memberCode);
}
