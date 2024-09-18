package com.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.dto.FetchingDto;
import com.hms.entity.Bill;
import com.hms.entity.Payment;
import com.hms.entity.SetRates;
import com.hms.exception.UserException;
import com.hms.service.BillService;
import com.hms.service.FetchingServiceImpl;
import com.hms.service.PaymentService;
import com.hms.service.SetRatesService;

@RestController
@RequestMapping("/bills")
public class HotelController {
	@Autowired
	private SetRatesService setRatesService;
	@Autowired
    private PaymentService paymentService;
	@Autowired
    private BillService billService;
	@Autowired
	private FetchingServiceImpl fetchingServiceImpl;
	
	

	@PostMapping("/setrates")
	@PreAuthorize("hasAnyAuthority('manager','receptionist')")
    public ResponseEntity<SetRates> createSetRates(@RequestBody SetRates setRates) throws UserException {
        SetRates savedSetRates = setRatesService.saveSetRates(setRates);
        return new ResponseEntity<SetRates>(savedSetRates, HttpStatus.OK);
    }
	
	
	@PostMapping("/fetching")
	//@PreAuthorize("hasAuthority('receptionist')")
    public ResponseEntity<FetchingDto> saveFetchingDetails(@RequestBody FetchingDto fetchingDto) {
       FetchingDto saveFetchingDetails = fetchingServiceImpl.saveFetchingDetails(fetchingDto);
        return new ResponseEntity<FetchingDto>(saveFetchingDetails, HttpStatus.OK);
    }

    @GetMapping("/setrates/{id}")
    @PreAuthorize("hasAnyAuthority('manager','receptionist','owner')")
    public ResponseEntity<SetRates> getSetRatesById(@PathVariable Long id) throws UserException {
        SetRates setRates = setRatesService.getSetRatesById(id);     
        return new ResponseEntity<SetRates>(setRates, HttpStatus.OK);
        
    }

    @GetMapping("/setrates")
    @PreAuthorize("hasAnyAuthority('manager','receptionist','owner')")
    public ResponseEntity<List<SetRates>> getAllSetRates() throws UserException {
        List<SetRates> setRatesList = setRatesService.getAllSetRates();
        return new ResponseEntity<List<SetRates>>(setRatesList, HttpStatus.OK);
    }

    @PostMapping("/payments")
    @PreAuthorize("hasAnyAuthority('manager','receptionist')")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) throws UserException {
        Payment savedPayment = paymentService.savePayment(payment);
        return new ResponseEntity<Payment>(savedPayment, HttpStatus.OK);
    }

    @GetMapping("/payments/{transactionId}")
    @PreAuthorize("hasAnyAuthority('manager','receptionist','owner')")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long transactionId) throws UserException {
        Payment payment = paymentService.getPaymentById(transactionId);
        return new ResponseEntity<Payment>(payment, HttpStatus.OK);
    }

    @GetMapping("/payments")
    @PreAuthorize("hasAnyAuthority('manager','receptionist','owner')")
    public ResponseEntity<List<Payment>> getAllPayments() throws UserException {
        List<Payment> paymentList = paymentService.getAllPayments();
        return new ResponseEntity<List<Payment>>(paymentList, HttpStatus.OK);
    }


    
    @PostMapping("/bills")
    @PreAuthorize("hasAnyAuthority('manager','receptionist')")
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) throws UserException {
    	Bill saveBill = billService.saveBill(bill);
    	return  new ResponseEntity<Bill>(saveBill, HttpStatus.OK);
        
    }
    

    @GetMapping("/bills/{billNumber}")
    @PreAuthorize("hasAnyAuthority('manager','receptionist','owner')")
    public ResponseEntity<Bill> getBillById(@PathVariable long billNumber) throws UserException {
        Bill bill = billService.getBillById(billNumber);
        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

    
    @GetMapping("/bills")
    @PreAuthorize("hasAnyAuthority('manager','receptionist','owner')")
    public ResponseEntity<List<Bill>> getAllBills() throws UserException {
        List<Bill> billList = billService.getAllBills();
        return new ResponseEntity<List<Bill>>(billList, HttpStatus.OK);
    }

    
    @GetMapping("/getamount")
	@PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Double> getTotalAmount() throws UserException {
    	double totalAmount = billService.totalAmount();
    	return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }
    
    
    @GetMapping("deleteFetchingDto/{id}")
    public void deleteFetchingDto(@PathVariable int id) {
    	
    	
    	fetchingServiceImpl.deleteFetchingDto(id);
    	
    }
	    
}
