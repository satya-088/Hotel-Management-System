package com.hms.entity;

public enum Roomtypes {
    STANDARD(100.00),
    LUXURY(150.00),
    PREMIUM(300.00);
 
    private final double amount;
 
    Roomtypes(double amount) {
        this.amount = amount;
    }
 
    public double getAmount() {
        return amount;
   }
   
}
