package com.hms.service;

import java.util.List;

import com.hms.entity.Reservation;
import com.hms.exception.CustomException;

public interface ReservationService {
	
	public Reservation saveReservations(Reservation reservation) throws CustomException;
	
	public Reservation getReservationDetailsById(int id) throws CustomException;
	
	
	public List<Reservation> listOfReservationsByDate()throws CustomException ;
	
	public String cancelReservationById(int id) throws CustomException;

}
