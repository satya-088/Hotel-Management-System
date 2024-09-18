package com.hms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.entity.Bill;
import com.hms.entity.Payment;
import com.hms.entity.PaymentStatus;
import com.hms.exception.UserException;
import com.hms.repository.BillRepository;
import com.hms.repository.PaymentRepository;

@Service
public class BillServiceImpementation implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    
    Logger log = LoggerFactory.getLogger(BillServiceImpementation.class);

    @Override
    public Bill saveBill(Bill bill) throws UserException {
        Long transactionId = bill.getPayment().getTransactionId();
        Optional<Payment> paymentOpt = paymentRepository.findById(transactionId);

        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            if (payment.getStatus() == PaymentStatus.COMPLETED) {
                bill.setTotalCost(payment.getTotalCost());
                bill.setPayment(payment);
                bill.setDate(LocalDateTime.now());
                
                log.info("Bill succesfully generated");
                return billRepository.save(bill);
            } else {
                throw new UserException("payment status is pending");
            }
        } else {
            throw new UserException("payment id is not found by "+transactionId);
        }
    }

    @Override
    public Bill getBillById(long billNumber) throws UserException {
        Optional<Bill> billOpt = billRepository.findById(billNumber);
        if (billOpt.isPresent()) {
            return billOpt.get();
        } else {
            throw new UserException("bill number not found by "+billNumber);
        }
    }

    @Override
    public List<Bill> getAllBills() throws UserException {
        List<Bill> allBills = billRepository.findAll();
        if (!allBills.isEmpty()) {
            return allBills;
        } else {
            throw new UserException("Bills list is empty");
        }
    }

    @Override
    public double totalAmount() throws UserException {
        List<Bill> allBills = getAllBills();
        if (allBills.isEmpty()) {
            throw new UserException("No payment recieved");
        } else {
            double totalAmount = 0;
            for (Bill bill : allBills) {
                totalAmount += bill.getTotalCost();
            }
            return totalAmount;
        }
    }
}


