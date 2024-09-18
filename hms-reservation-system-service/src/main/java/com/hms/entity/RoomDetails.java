package com.hms.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true,nullable = false)
	@NotNull(message =  "choose valid available room no and mandatory")
	private String roomNo;
	@NotNull(message = "room type is mandatory")
	@Enumerated(EnumType.STRING)
	private Roomtypes roomType;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private double amount;
	
	//private String status;
	
	
	
}




//enum Roomtypes{
//	
//	STANDARD,
//	LUXURY,
//	PREMIUM
//	
//}
//
//enum Status{
//	AVAILABLE,
//	UNAVIALABLE
//}

