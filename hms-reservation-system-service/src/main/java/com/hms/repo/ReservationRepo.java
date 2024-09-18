package com.hms.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.entity.Reservation;
import com.hms.entity.RoomDetails;

public interface ReservationRepo  extends JpaRepository<Reservation,Integer>{
	
	
	public Optional<List<Reservation>> findByCheckOutAfter(LocalDate date);
	
	

}
