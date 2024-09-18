package com.hms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
	@Id
	@NotNull(message = "Employee id should not be null")
	@Min(value =20230101, message = "emp Id must be less than or equal to 8 characters")
	@Max(value =20230199 , message = "emp Id must be less than or equal to 8 characters")
	private long code;
	@NotBlank(message = "empNamae is required")
	@Size(max = 25, message = "emp name must be less than or equal to 25 characters")
	private String employeeName;
	private String employeeAddress;
	private float salary;
	private int age;
	private String occupation;
	@NotBlank(message = "Email should not be null")
	@Email(message = "Email is Required")
	@Size(max = 15,message = "Email should less than or equal 15 character")
	@Pattern(regexp = "[a-z]{1,6}@{1}[a-z]{5}\\.com")
	private String email;
	

	
}
