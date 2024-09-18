package com.hms.dto;

public enum Roomtype {
    STANDARD(100.00),
    LUXURY(150.00),
    PREMIUM(300.00);
 
    private final double amount;
 
    Roomtype(double amount) {
        this.amount = amount;
    }
 
    public double getAmount() {
        return amount;
   }
   
}
