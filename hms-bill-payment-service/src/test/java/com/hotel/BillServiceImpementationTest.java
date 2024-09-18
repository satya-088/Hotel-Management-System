package com.hotel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hms.entity.Bill;
import com.hms.entity.Payment;
import com.hms.entity.PaymentStatus;
import com.hms.exception.UserException;
import com.hms.repository.BillRepository;
import com.hms.repository.PaymentRepository;
import com.hms.service.BillServiceImpementation;

public class BillServiceImpementationTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private BillServiceImpementation billService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testSaveBill_Success() throws UserException {
        // Arrange
        Bill bill = new Bill();
        bill.setBillNumber(5001L);

        Payment payment = new Payment();
        payment.setTransactionId(1001L);
        payment.setTotalCost(200.0);
        payment.setStatus(PaymentStatus.COMPLETED);
        bill.setPayment(payment);

        when(paymentRepository.findById(1001L)).thenReturn(Optional.of(payment));
        when(billRepository.save(any(Bill.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Bill result = billService.saveBill(bill);

        // Assert
        assertNotNull(result);
        assertEquals(200.0, result.getTotalCost(), 0.01);
        assertEquals(payment, result.getPayment());
        assertNotNull(result.getDate());
    }
    @Test
    public void testSaveBill_PaymentNotFound() throws UserException {
        // Arrange
        Bill bill = new Bill();
        bill.setBillNumber(5001L);

        Payment payment = new Payment();
        payment.setTransactionId(1001L);
        bill.setPayment(payment);

        when(paymentRepository.findById(1001L)).thenReturn(Optional.empty());

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> billService.saveBill(bill));
        assertEquals("payment id is not found", exception.getMessage());
    }
    @Test
    public void testSaveBill_PaymentNotCompleted() throws UserException {
        // Arrange
        Bill bill = new Bill();
        bill.setBillNumber(5001L);

        Payment payment = new Payment();
        payment.setTransactionId(1001L);
        payment.setStatus(PaymentStatus.PENDING); // Status is not completed
        bill.setPayment(payment);

        when(paymentRepository.findById(1001L)).thenReturn(Optional.of(payment));

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> billService.saveBill(bill));
        assertEquals("payment id is not found", exception.getMessage());
    }
    @Test
    public void testGetBillById_Success() throws UserException {
        // Arrange
        Bill bill = new Bill();
        bill.setBillNumber(5001L);

        when(billRepository.findById(5001L)).thenReturn(Optional.of(bill));

        // Act
        Bill result = billService.getBillById(5001L);

        // Assert
        assertNotNull(result);
        assertEquals(5001L, result.getBillNumber());
    }
    @Test
    public void testGetBillById_BillNotFound() throws UserException {
        // Arrange
        when(billRepository.findById(5001L)).thenReturn(Optional.empty());

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> billService.getBillById(5001L));
        assertEquals("id is not found", exception.getMessage());
    }
    @Test
    public void testGetAllBills_Success() throws UserException {
        // Arrange
        Bill bill1 = new Bill();
        Bill bill2 = new Bill();
        List<Bill> bills = Arrays.asList(bill1, bill2);

        when(billRepository.findAll()).thenReturn(bills);

        // Act
        List<Bill> result = billService.getAllBills();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    public void testGetAllBills_Empty() throws UserException {
        // Arrange
        when(billRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> billService.getAllBills());
        assertEquals("Bills list is empty", exception.getMessage());
    }
    @Test
    public void testTotalAmount_Success() throws UserException {
        // Arrange
        Bill bill1 = new Bill();
        bill1.setTotalCost(100.0);

        Bill bill2 = new Bill();
        bill2.setTotalCost(150.0);

        List<Bill> bills = Arrays.asList(bill1, bill2);

        when(billRepository.findAll()).thenReturn(bills);

        // Act
        double result = billService.totalAmount();

        // Assert
        assertEquals(250.0, result, 0.01);
    }
}
//    