package com.hms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
	@Id
	private int id;
	private String itemName;
	private int quantity;
	private double itemPrice;



}
