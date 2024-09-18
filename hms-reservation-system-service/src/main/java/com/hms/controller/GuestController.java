package com.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.entity.Guest;
import com.hms.exception.CustomException;
import com.hms.service.GuestService;



@RestController
@RequestMapping("guest")
public class GuestController {
   
	@Autowired
	private GuestService guestService;
	

	
	@GetMapping("/all")
	public List<Guest> findAllGuestDetails(){
		List<Guest> list = guestService.findAllGuestDetails();
		return list;
		
	//	return guestService.findAllGuestDetails();
		
	}
	
	@PostMapping("/display/{memberCode}")
	public  ResponseEntity<Guest> displayGuestDetails(@PathVariable int memberCode) throws CustomException {
		Guest guest= guestService.displayGuestDetails(memberCode);
		ResponseEntity<Guest> rs=new ResponseEntity<Guest>(guest,HttpStatus.OK);
		return rs;
		
	}
	
	@DeleteMapping("/delete/{memberCode}")
	public void deleteGuest(@PathVariable int memberCode) {

    guestService.deleteGuest(memberCode);

	}
	
	
	
//	@PostMapping("/add")
//	@PreAuthorize("hasAuthority('receptionist')")
//	public Guest addGuest(@RequestBody Guest guest) {
//		
//		Guest gt = guestService.addGuest(guest);
//		return gt;
//		
//		//return guestService.addGuest(guest);
//		
//	}

	}
