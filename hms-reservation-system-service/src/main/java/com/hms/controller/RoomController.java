package com.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.entity.RoomDetails;
import com.hms.entity.UserCheckRoomDetails;
import com.hms.exception.CustomException;
import com.hms.service.RoomDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("rooms")
public class RoomController {

	@Autowired
	private RoomDetailsService detailsService;

	@GetMapping("/say")
	@PreAuthorize("hasAuthority('owner')")
	public String getMessage() {

		return "Hello .......";
	}

	@PostMapping("saveRoomDetails")
	@PreAuthorize("hasAuthority('owner')")
	public ResponseEntity<List<RoomDetails>> saveAllRoomDetails(@Valid @RequestBody List<RoomDetails> details) {

		List<RoomDetails> allRoomDetails = detailsService.addAllRoomDetails(details);

		return new ResponseEntity<List<RoomDetails>>(allRoomDetails, HttpStatus.OK);

	}



	@GetMapping("userCheckRoomDetails")
	public List<UserCheckRoomDetails> userCheckRoomDetails()throws CustomException {

		List<UserCheckRoomDetails> checkListOfRoomDetails = detailsService.checkListOfRoomDetails();

		return checkListOfRoomDetails;

	}
	
	@GetMapping("listOfRoomDetailsByFloor/{num}")
	@PreAuthorize("hasAnyAuthority('manager','owner','receptionist')")
	public ResponseEntity<List<UserCheckRoomDetails>> listOfRoomDetailsByFloorNo(@PathVariable int num)throws CustomException{
		List<UserCheckRoomDetails> listOfRoomDetailsByFloorNo = detailsService.listOfRoomDetailsByFloorNo(num);
		
		return new ResponseEntity<List<UserCheckRoomDetails>>(listOfRoomDetailsByFloorNo,HttpStatus.OK);
		
		
	}

}
