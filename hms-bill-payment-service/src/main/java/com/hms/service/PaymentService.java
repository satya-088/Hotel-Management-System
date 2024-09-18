package com.hms.service;

import java.util.List;
import java.util.Optional;

import com.hms.entity.Payment;
import com.hms.exception.UserException;

public interface PaymentService {
	
	 Payment savePayment(Payment payment)throws UserException;

	 Payment getPaymentById(Long transactionId)throws UserException;

	 List<Payment> getAllPayments()throws UserException;

}
