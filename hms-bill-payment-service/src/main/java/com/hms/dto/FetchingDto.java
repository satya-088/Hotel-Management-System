package com.hms.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchingDto {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int memberCode;
	private String roomNum;
	@Enumerated(EnumType.STRING)
	private Roomtype type;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private String guestName;
	private String email;
	private long mobileNo;
	private String gender;
	private String Address;
	private double advanceAmount;
	
}
