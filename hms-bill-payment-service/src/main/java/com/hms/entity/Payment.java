package com.hms.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {	

	 @Id
	 @NotNull
	 @Min(value=1000)
	 @Max(value=10000)
	 private Long transactionId;
	 private double priceOfRoomsAndServices;
	 private double taxes;
	 @Enumerated(EnumType.STRING)
	 private Services services;
	 private LocalDateTime payTime;

	 @Enumerated(EnumType.STRING)
	 private PaymentType paymentType;

	 @Enumerated(EnumType.STRING)
	 private PaymentStatus status;
	  
	 private double totalCost;
	 @OneToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name = "setratesid", referencedColumnName = "id")
	 private SetRates setRates;  
}

enum PaymentType {
	CASH,UPI,CREDIT_CARD, DEBIT_CARD, OTHER
}




