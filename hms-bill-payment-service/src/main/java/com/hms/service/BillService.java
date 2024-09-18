package com.hms.service;

import java.util.List;
import java.util.Optional;

import com.hms.entity.Bill;
import com.hms.exception.UserException;

public interface BillService {
	
	 Bill saveBill(Bill bill)throws UserException;

	 Bill getBillById(long billNumber)throws UserException;

	 List<Bill> getAllBills()throws UserException;


     double totalAmount()throws UserException;
	
}
