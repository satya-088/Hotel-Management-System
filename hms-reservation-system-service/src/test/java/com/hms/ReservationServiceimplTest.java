package com.hms;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
 
import com.hms.entity.Guest;
import com.hms.entity.Reservation;
import com.hms.entity.RoomDetails;
import com.hms.exception.CustomException;
import com.hms.repo.GuestRepository;
import com.hms.repo.ReservationRepo;
import com.hms.repo.RoomDetailsRepo;
import com.hms.service.ReservationServiceimpl;
import com.hms.service.SetRatesClient;
 
@ExtendWith(MockitoExtension.class)
public class ReservationServiceimplTest {
 
    @InjectMocks
    private ReservationServiceimpl reservationService;
 
    @Mock
    private ReservationRepo reservationRepo;
 
    @Mock
    private GuestRepository guestRepository;
 
    @Mock
    private RoomDetailsRepo roomDetailsRepo;
 
    @Mock
    private SetRatesClient setRatesClient;
 
    @Mock
    private JavaMailSender javaMailSender;
 
    @BeforeEach
    public void setUp() {
        // No need for MockitoAnnotations.initMocks(this) as we are using @ExtendWith(MockitoExtension.class)
    }
 
//    @Test
//    public void testSavesreservations_Success() throws CustomException {
//        // Arrange
//        Reservation reservation = new Reservation();
//        reservation.setRoomNum("101");
//        reservation.setCheckIn(LocalDate.now());
//        reservation.setCheckOut(LocalDate.now().plusDays(1));
//        reservation.setGuestName("John Doe");
//        reservation.setEmail("john.doe@example.com");
//        reservation.setMobileNo(6000000000L);
//        reservation.setGender("Male");
//
//        RoomDetails roomDetails = new RoomDetails();
//        roomDetails.setRoomNo("101");
//
//        // Mock the behavior of dependencies
//        when(setRatesClient.saveGuestDetails(any(Reservation.class))).thenReturn(reservation);
//        when(reservationRepo.save(any(Reservation.class))).thenReturn(reservation);
//        when(roomDetailsRepo.findByRoomNo(anyString())).thenReturn(roomDetails);
//        when(roomDetailsRepo.save(any(RoomDetails.class))).thenReturn(roomDetails); // Ensure save is mocked
//
//        // Act
//        Reservation result = reservationService.savereservations(reservation);
//
//        // Assert
//        assertEquals("101", result.getRoomNum());
//        assertEquals(LocalDate.now(), result.getCheckIn());
//        assertEquals(LocalDate.now().plusDays(1), result.getCheckOut());
//
//        verify(reservationRepo, times(1)).save(any(Reservation.class));
//        verify(roomDetailsRepo, times(1)).findByRoomNo(anyString());
//        verify(roomDetailsRepo, times(1)).save(any(RoomDetails.class));
//        verify(guestRepository, times(1)).save(any(Guest.class));
//
//        // Capture the argument passed to javaMailSender.send()
//        ArgumentCaptor<SimpleMailMessage> mailMessageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
//        verify(javaMailSender, times(1)).send(mailMessageCaptor.capture());
//
//        SimpleMailMessage capturedMailMessage = mailMessageCaptor.getValue();
//
//        // Perform assertions on the captured mail message
//        assertEquals("nikhilanthireddy939@gmail.com", capturedMailMessage.getFrom());
//        assertEquals("john.doe@example.com", capturedMailMessage.getTo()[0]);
//        assertEquals("HMS Reservation", capturedMailMessage.getSubject());
//        assertEquals("Your Room Reservation Succesfully by Id is " + result.getMemberCode() + " and room number is " + result.getRoomNum(),
//                     capturedMailMessage.getText());
//    }
 
    @Test
    public void testSavesreservations_Failure_SetRatesClient() throws CustomException {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setRoomNum("101");
        reservation.setCheckIn(LocalDate.now());
        reservation.setCheckOut(LocalDate.now().plusDays(1));
        reservation.setGuestName("John Doe");
        reservation.setEmail("john.doe@example.com");
        reservation.setMobileNo(6000000000L);
        reservation.setGender("Male");
 
        when(setRatesClient.saveGuestDetails(any(Reservation.class))).thenThrow(new RuntimeException("Error from setRatesClient"));
 
        // Act & Assert
        try {
            reservationService.saveReservations(reservation);
        } catch (RuntimeException e) {
            assertEquals("Error from setRatesClient", e.getMessage());
        }
 
        verify(setRatesClient, times(1)).saveGuestDetails(any(Reservation.class));
        verify(reservationRepo, never()).save(any(Reservation.class));
        verify(roomDetailsRepo, never()).findByRoomNo(anyString());
        verify(roomDetailsRepo, never()).save(any(RoomDetails.class));
        verify(guestRepository, never()).save(any(Guest.class));
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }
 
    @Test
    public void testSavesreservations_Failure_ReservationRepo() throws CustomException {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setRoomNum("101");
        reservation.setCheckIn(LocalDate.now());
        reservation.setCheckOut(LocalDate.now().plusDays(1));
        reservation.setGuestName("John Doe");
        reservation.setEmail("john.doe@example.com");
        reservation.setMobileNo(6000000000L);
        reservation.setGender("Male");
 
        RoomDetails roomDetails = new RoomDetails();
        roomDetails.setRoomNo("101");
 
        when(setRatesClient.saveGuestDetails(any(Reservation.class))).thenReturn(reservation);
        when(reservationRepo.save(any(Reservation.class))).thenThrow(new RuntimeException("Error saving reservation"));
 
        // Act & Assert
        try {
            reservationService.saveReservations(reservation);
        } catch (RuntimeException e) {
            assertEquals("Error saving reservation", e.getMessage());
        }
 
        verify(setRatesClient, times(1)).saveGuestDetails(any(Reservation.class));
        verify(reservationRepo, times(1)).save(any(Reservation.class));
        verify(roomDetailsRepo, never()).findByRoomNo(anyString());
        verify(roomDetailsRepo, never()).save(any(RoomDetails.class));
        verify(guestRepository, never()).save(any(Guest.class));
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }
    @Test
    public void testGetReservationDetailsById() throws CustomException {
        Reservation reservation = new Reservation();
        reservation.setMemberCode(1);
 
        when(reservationRepo.findById(anyInt())).thenReturn(Optional.of(reservation));
 
        Reservation result = reservationService.getReservationDetailsById(1);
 
        assertEquals(1, result.getMemberCode());
        verify(reservationRepo, times(1)).findById(anyInt());
    }
 
    @Test
    public void testGetReservationDetailsByIdNotFound() {
        when(reservationRepo.findById(anyInt())).thenReturn(Optional.empty());
 
        CustomException exception = assertThrows(CustomException.class, () -> {
            reservationService.getReservationDetailsById(1);
        });
 
        assertEquals("Your Reservation details not found by 1", exception.getMessage());
        verify(reservationRepo, times(1)).findById(anyInt());
    }
 
    @Test
    public void testListOfReservationsByDate() throws CustomException {
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservations.add(reservation);
 
        when(reservationRepo.findByCheckOutAfter(any(LocalDate.class))).thenReturn(Optional.of(reservations));
 
        List<Reservation> result = reservationService.listOfReservationsByDate();
 
        assertEquals(1, result.size());
        verify(reservationRepo, times(1)).findByCheckOutAfter(any(LocalDate.class));
    }
 
    @Test
    public void testListOfReservationsByDateEmpty() {
        when(reservationRepo.findByCheckOutAfter(any(LocalDate.class))).thenReturn(Optional.empty());
 
        CustomException exception = assertThrows(CustomException.class, () -> {
            reservationService.listOfReservationsByDate();
        });
 
        assertEquals("Reservation data is empty", exception.getMessage());
        verify(reservationRepo, times(1)).findByCheckOutAfter(any(LocalDate.class));
    }
}