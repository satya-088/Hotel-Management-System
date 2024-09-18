package com.hms.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int memberCode;
	@NotNull(message = "choose & enter the valid avilable room number")
	private String roomNum;
	@Enumerated(EnumType.STRING)
	private Roomtype type;
	@NotNull(message = "check in date is mandatory")
	private LocalDate checkIn;
	@NotNull(message = "checkout date is mandatory")
	private LocalDate checkOut;
	@NotNull(message = "name is mandatory")
	private String guestName;
	
	@NotNull(message = "email is mandatory")
	private String email;
	
	
	@Min(value = 6000000000L,message = "please enter valid mobile number")
	@Max(value = 9999999999L,message = "please enter valid mobile number")
	private long mobileNo;
	private String gender;
	private String Address;
	@Min(value = 55,message = "Adavance amount min 55.0 Rs pay")
	private double advanceAmount;
	
	
	
	
}






enum Roomtype{
	
	STANDARD,
	LUXURY,
	PREMIUM
	
}