package com.hms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.hms.dto.FetchingDto;

@Entity
@Data
public class SetRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private LocalDate checkIn;
    //private LocalDate checkOut;
    
    private double pricePerDay;
    @Enumerated(EnumType.STRING)
    private ExtensionPrice extensionPrice;
    private double price;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fetchingCode",referencedColumnName = "memberCode")
    private FetchingDto fetchingDto;
    
    
   
}



