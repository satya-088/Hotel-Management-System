package com.hotel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hms.entity.Payment;
import com.hms.entity.Services;
import com.hms.entity.SetRates;
import com.hms.exception.UserException;
import com.hms.repository.PaymentRepository;
import com.hms.repository.SetRatesRepository;
import com.hms.service.PaymentServiceImplementation;

public class PaymentServiceImplementationTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private SetRatesRepository setRatesRepository;

    @InjectMocks
    private PaymentServiceImplementation paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSavePayment_Success() throws UserException {
        // Arrange
        SetRates setRates = new SetRates();
        setRates.setId(1L);
        setRates.setPrice(200.0); // Sample price

        Payment payment = new Payment();
        payment.setTransactionId(1001L);
        payment.setServices(Services.ROOMSERVICE);
        payment.setSetRates(setRates);

        when(setRatesRepository.findById(1L)).thenReturn(Optional.of(setRates));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Payment result = paymentService.savePayment(payment);

        // Assert
        assertNotNull(result);
        assertEquals(setRates, result.getSetRates());
        double expectedPriceOfRoomsAndServices = setRates.getPrice() + payment.getServices().getAmount();
        assertEquals(expectedPriceOfRoomsAndServices, result.getPriceOfRoomsAndServices(), 0.01);
        double expectedTaxes = expectedPriceOfRoomsAndServices * 0.10;
        assertEquals(expectedTaxes, result.getTaxes(), 0.01);
        double expectedTotalCost = expectedPriceOfRoomsAndServices + expectedTaxes;
        assertEquals(expectedTotalCost, result.getTotalCost(), 0.01);
        assertNotNull(result.getPayTime());
    }

    @Test
    public void testSavePayment_SetRatesNotFound() {
        // Arrange
        Payment payment = new Payment();
        payment.setTransactionId(1001L);
        payment.setServices(Services.ROOMSERVICE);
        payment.setSetRates(new SetRates());

        when(setRatesRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> paymentService.savePayment(payment));
        assertEquals("SetRates  id is not found", exception.getMessage());
    }

    @Test
    public void testGetPaymentById_Success() throws UserException {
        // Arrange
        Payment payment = new Payment();
        payment.setTransactionId(1001L);

        when(paymentRepository.findById(1001L)).thenReturn(Optional.of(payment));

        // Act
        Payment result = paymentService.getPaymentById(1001L);

        // Assert
        assertNotNull(result);
        assertEquals(1001L, result.getTransactionId());
    }

    @Test
    public void testGetPaymentById_IdNotFound() {
        // Arrange
        when(paymentRepository.findById(1001L)).thenReturn(Optional.empty());

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> paymentService.getPaymentById(1001L));
        assertEquals("SetRates  id is not found", exception.getMessage());
    }

    @Test
    public void testGetAllPayments_Success() throws UserException {
        // Arrange
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();

        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment1, payment2));

        // Act
        List<Payment> result = paymentService.getAllPayments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllPayments_Empty() {
        // Arrange
        when(paymentRepository.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> paymentService.getAllPayments());
        assertEquals("Payments list is empty", exception.getMessage());
    }
}

//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//class PaymentServiceImplementationTest {
//
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
//
//}
