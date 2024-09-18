package com.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hms.entity.Guest;
import com.hms.exception.CustomException;
import com.hms.repo.GuestRepository;
import com.hms.service.GuestServiceImpl;
 
public class GuestServiceImplTest {
 
    @InjectMocks
    private GuestServiceImpl guestService;
 
    @Mock
    private GuestRepository guestRepository;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    public void testFindAllGuestDetails_Success() {
        // Arrange
        Guest guest1 = new Guest();
        Guest guest2 = new Guest();
        List<Guest> guestList = Arrays.asList(guest1, guest2);
 
        when(guestRepository.findAll()).thenReturn(guestList);
 
        // Act
        List<Guest> result = guestService.findAllGuestDetails();
 
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(guestRepository, times(1)).findAll();
    }
 
    @Test
    public void testDisplayGuestDetails_Success() throws CustomException {
        // Arrange
        int memberCode = 123;
        Guest guest = new Guest();
        when(guestRepository.findById(memberCode)).thenReturn(Optional.of(guest));
 
        // Act
        Guest result = guestService.displayGuestDetails(memberCode);
 
        // Assert
        assertNotNull(result);
        verify(guestRepository, times(1)).findById(memberCode);
    }
 
   
    @Test
    public void testDeleteGuest_Success() {
        // Arrange
        int memberCode = 123;
 
        // Act
        guestService.deleteGuest(memberCode);
 
        // Assert
        verify(guestRepository, times(1)).deleteById(memberCode);
    }
}