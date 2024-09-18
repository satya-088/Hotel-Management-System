package com.hms.service;

import java.util.List;

import com.hms.entity.RoomDetails;
import com.hms.entity.UserCheckRoomDetails;
import com.hms.exception.CustomException;

public interface RoomDetailsService {
	
	public List<RoomDetails> addAllRoomDetails(List<RoomDetails> rooms);
	
	
	public List<UserCheckRoomDetails> checkListOfRoomDetails() throws CustomException;
	
	public List<UserCheckRoomDetails> listOfRoomDetailsByFloorNo(int num)throws CustomException;
	

	
	
}
