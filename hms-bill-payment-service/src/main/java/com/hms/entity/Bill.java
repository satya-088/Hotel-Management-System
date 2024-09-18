package com.hms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Bill {
	@Id
	@NotNull
	@Min(value=10000)
	@Max(value=100000)
    private Long billNumber;
	@NotNull
    private LocalDateTime date;
    private double totalCost;
   
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentId",referencedColumnName = "transactionId")
    private Payment payment;
     
 }
