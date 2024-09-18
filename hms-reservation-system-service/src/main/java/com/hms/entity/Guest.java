package com.hms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guest {
	
	
	@Id
	private int memberCode;
	//@NotNull(message = "phone number is mandatory")
	private long phoneNumber;
	
	//private String company;
	//@NotBlank(message = "name shoud not be empty")
	private String name;

	private String email;
	private String gender;
	private String address;
	
	
	
	

}
