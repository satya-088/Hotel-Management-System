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

import com.hms.entity.Reservation;
import com.hms.exception.CustomException;
import com.hms.service.ReservationService;
import com.hms.service.SetRatesClient;

import jakarta.validation.Valid;

@RestController
@RequestMapping("reservation")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	SetRatesClient ratesClient;
	
	
//	@GetMapping("/say")
//	@PreAuthorize("hasAuthority('receptionist')")
//	public String getMessage() {
//		
//		return "Hello .......";
//	}
	
	
	@PostMapping("/saveReservations")
	//@PreAuthorize("hasAnyAuthority('receptionist','admin')")
	public ResponseEntity<Reservation> saveReservationDetails(@Valid @RequestBody Reservation reservation)throws CustomException {
		
		Reservation savereservations = reservationService.saveReservations(reservation);
		
		return new ResponseEntity<Reservation>(savereservations,HttpStatus.OK);
		
		
		
	}
	
	
	@GetMapping("getByIdReservation/{id}")
	public ResponseEntity<Reservation> getReservationDetailsById(@PathVariable int id) throws CustomException {
		
		Reservation reservationDetailsById = reservationService.getReservationDetailsById(id);
		
		return new ResponseEntity<Reservation>(reservationDetailsById,HttpStatus.OK);
		
		
	}
	
	@GetMapping("listOfReservations")
	@PreAuthorize("hasAnyAuthority('receptionist','manager','owner')")
	public ResponseEntity<List<Reservation>> listOfReservationsByDate()throws CustomException {
		
		List<Reservation> listOfReservationsByDate = reservationService.listOfReservationsByDate();
		
		return new ResponseEntity<List<Reservation>>(listOfReservationsByDate,HttpStatus.OK);
		
	}
	
	@GetMapping("cancelReservations/{id}")
	public ResponseEntity<String> cancelReservationById(@PathVariable int id) throws CustomException{
		
		
		String cancelReservationById = reservationService.cancelReservationById(id);
		
		return new ResponseEntity<String>(cancelReservationById,HttpStatus.OK);
	}
	
	

	
	
	
	
//	@PostMapping("/msg")
//	@PreAuthorize("hasAuthority('receptionist')")
//	public String getMessage(@RequestBody Reservation reservation) {
//		
//		return ratesClient.getMessage();
//		
//		
//	}
	

}
