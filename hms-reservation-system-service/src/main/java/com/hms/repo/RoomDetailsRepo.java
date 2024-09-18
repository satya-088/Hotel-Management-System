package com.hms.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hms.entity.Reservation;
import com.hms.entity.RoomDetails;
import com.hms.entity.UserCheckRoomDetails;



public interface RoomDetailsRepo extends JpaRepository<RoomDetails,Integer>{
	
	
	//public Optional<List<RoomDetails>> findByStatus(String status);
	
	public Optional<List<RoomDetails>> findByCheckOutBefore(LocalDate date);
	
	@Query("SELECT new com.hms.entity.UserCheckRoomDetails(r.id, r.roomNo, r.roomType,r.amount) FROM RoomDetails r")
	List<UserCheckRoomDetails> findAllRoomDetailsDTO();
	
	public RoomDetails findByRoomNo(String roomNo);

}
