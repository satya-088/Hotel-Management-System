package com.hms.entity;

public enum ExtensionPrice {
	NONE(1.00),
    STANDARD(70.00),
    LUXURY(90.00),
    PREMIUM(225.00);

    private final double amount;

    ExtensionPrice(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}