package com.hms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hms.entity.Payment;
import com.hms.entity.Services;
import com.hms.entity.SetRates;
import com.hms.exception.ErrorResponse;
import com.hms.exception.UserException;
import com.hms.repository.PaymentRepository;
import com.hms.repository.SetRatesRepository;

@Service
public class PaymentServiceImplementation implements PaymentService{
	@Autowired
	private  PaymentRepository paymentRepository;
    @Autowired
    private SetRatesRepository setRatesRepository;
    
    Logger log = LoggerFactory.getLogger(PaymentServiceImplementation.class);
    		
   
    @Override
    public Payment savePayment(Payment payment) throws UserException {
        Optional<SetRates> byId = setRatesRepository.findById(payment.getSetRates().getId());
        if(byId.isPresent()) {
        	SetRates setRates = byId.get();     
            payment.setPriceOfRoomsAndServices(setRates.getPrice()+payment.getServices().getAmount());
            payment.setSetRates(setRates);
            double priceOfRooms = payment.getPriceOfRoomsAndServices();
            payment.setTaxes((priceOfRooms*10)/100);
            payment.setTotalCost(payment.getPriceOfRoomsAndServices()+payment.getTaxes());
            payment.setPayTime(LocalDateTime.now());
            
            log.info("payment successfully saved");
            return paymentRepository.save(payment);
        }
        else throw new UserException("set rates not found by"+byId.get().getId());
    	
    }

    @Override
    public Payment getPaymentById(Long transactionId) throws UserException {
    	Optional<Payment> byId = paymentRepository.findById(transactionId);
    	if(byId.isPresent()) {
    		
    		log.info("payment retrived for given id");
    		return byId.get();
    	}
    	else throw new UserException("payment not found by "+transactionId);
    }

    @Override
    public List<Payment> getAllPayments() throws UserException {
    	List<Payment> all = paymentRepository.findAll();
    	if (!all.isEmpty()) {
    		return paymentRepository.findAll();
    	}
    	else throw new UserException("Payments list is empty");
    }


    
}
