package com.hms;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hms.entity.Reservation;
import com.hms.entity.RoomDetails;
import com.hms.entity.Roomtypes;
import com.hms.entity.UserCheckRoomDetails;
import com.hms.exception.CustomException;
import com.hms.repo.ReservationRepo;
import com.hms.repo.RoomDetailsRepo;
import com.hms.service.RoomDetailsServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class RoomDetailsServiceImplTest {

    @InjectMocks
    private RoomDetailsServiceImpl roomDetailsService;

    @Mock
    private RoomDetailsRepo detailsRepo;

    @Mock
    private ReservationRepo reserveRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAllRoomDetails() {
        List<RoomDetails> rooms = new ArrayList<>();
        RoomDetails room = new RoomDetails();
        room.setRoomType(Roomtypes.STANDARD);
        rooms.add(room);
 
        when(detailsRepo.saveAll(anyList())).thenReturn(rooms);
 
        List<RoomDetails> result = roomDetailsService.addAllRoomDetails(rooms);
 
        assertEquals(1, result.size());
        assertEquals(100.00, result.get(0).getAmount(), 0);
        verify(detailsRepo, times(1)).saveAll(anyList());
    }


    @Test
    void testCheckListOfRoomDetails_AvailableRooms() throws CustomException {
        // Arrange
        List<UserCheckRoomDetails> availableRooms = new ArrayList<>();
        UserCheckRoomDetails roomDetail = new UserCheckRoomDetails();
        roomDetail.setRoomNo("101");
        availableRooms.add(roomDetail);

        List<Reservation> reservations = new ArrayList<>();

        when(detailsRepo.findAllRoomDetailsDTO()).thenReturn(availableRooms);
        when(reserveRepo.findAll()).thenReturn(reservations);

        // Act
        List<UserCheckRoomDetails> rooms = roomDetailsService.checkListOfRoomDetails();

        // Assert
        assertEquals(1, rooms.size());
        assertEquals("101", rooms.get(0).getRoomNo());
    }

    @Test
    void testCheckListOfRoomDetails_NoAvailableRooms() {
        // Arrange
        List<UserCheckRoomDetails> availableRooms = new ArrayList<>();
        List<Reservation> reservations = new ArrayList<>();
        
        // Assuming reservation for room 101
        Reservation reservation = new Reservation();
        reservation.setRoomNum("101");
        reservations.add(reservation);

        when(detailsRepo.findAllRoomDetailsDTO()).thenReturn(availableRooms);
        when(reserveRepo.findAll()).thenReturn(reservations);

        // Act & Assert
        assertThrows(CustomException.class, () -> {
            roomDetailsService.checkListOfRoomDetails();
        });
    }

    @Test
    void testListOfRoomDetailsByFloorNo_FloorFound() throws CustomException {
        // Arrange
        List<UserCheckRoomDetails> allRooms = new ArrayList<>();
        
        // Adding room details for floor 1
        UserCheckRoomDetails roomDetail1 = new UserCheckRoomDetails();
        roomDetail1.setRoomNo("101"); // Valid for floor 1
        allRooms.add(roomDetail1);
        
        UserCheckRoomDetails roomDetail2 = new UserCheckRoomDetails();
        roomDetail2.setRoomNo("102"); // Valid for floor 1
        allRooms.add(roomDetail2);

        // Mock the repository to return the above room details
        when(detailsRepo.findAllRoomDetailsDTO()).thenReturn(allRooms);

        // Act
        List<UserCheckRoomDetails> roomsByFloor = roomDetailsService.listOfRoomDetailsByFloorNo(1);

        // Assert
        assertEquals(2, roomsByFloor.size()); // Expecting both room 101 and 102
        assertEquals("101", roomsByFloor.get(0).getRoomNo());
        assertEquals("102", roomsByFloor.get(1).getRoomNo());
    }

    @Test
    void testListOfRoomDetailsByFloorNo_FloorNotFound() {
        // Arrange
        List<UserCheckRoomDetails> allRooms = new ArrayList<>();
        UserCheckRoomDetails roomDetail1 = new UserCheckRoomDetails();
        roomDetail1.setRoomNo("201"); // Not valid for floor 1
        allRooms.add(roomDetail1);

        when(detailsRepo.findAllRoomDetailsDTO()).thenReturn(allRooms);

        // Act & Assert
        assertThrows(CustomException.class, () -> {
            roomDetailsService.listOfRoomDetailsByFloorNo(1);
        });
    }
}
