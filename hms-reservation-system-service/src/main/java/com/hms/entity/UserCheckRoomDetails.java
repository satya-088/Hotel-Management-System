package com.hms.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCheckRoomDetails {
	
	private int id;
	private String roomNo;
	@Enumerated(EnumType.STRING)
	private Roomtypes roomType;
	private double amount;

}
