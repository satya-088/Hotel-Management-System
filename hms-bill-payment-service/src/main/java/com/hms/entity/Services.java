package com.hms.entity;

public enum Services {
	NONE(1.00),
	ROOMSERVICE(100.00),
	FOODSERVICE(150.00),
	TRAVELSERVICE(50.00);

	private final double serviceAmount;
    Services(double serivceAmount){
    	this.serviceAmount=serivceAmount;
    }
    public double getAmount() {
        return serviceAmount;
   }
}
