package com.hotel;




import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hms.dto.FetchingDto;
import com.hms.dto.Roomtype;
import com.hms.entity.ExtensionPrice;
import com.hms.entity.SetRates;
import com.hms.exception.UserException;
import com.hms.repository.FetchingRepository;
import com.hms.repository.SetRatesRepository;
import com.hms.service.SetRatesServiceImplementation;

public class SetRatesServiceImplementationTest {

    @Mock
    private SetRatesRepository setRatesRepository;

    @Mock
    private FetchingRepository fetchingRepository;

    @InjectMocks
    private SetRatesServiceImplementation setRatesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSetRates_Success() throws UserException {
        // Arrange
        FetchingDto fetchingDto = new FetchingDto();
        fetchingDto.setMemberCode(1);
        fetchingDto.setType(Roomtype.STANDARD);
        fetchingDto.setCheckIn(LocalDate.of(2024, 9, 1));
        fetchingDto.setCheckOut(LocalDate.of(2024, 9, 5));
        fetchingDto.setAdvanceAmount(50.0);

        SetRates setRates = new SetRates();
        setRates.setFetchingDto(fetchingDto);
        setRates.setExtensionPrice(ExtensionPrice.STANDARD);  // Assuming ExtensionPrice is an enum

        when(fetchingRepository.findById(1)).thenReturn(Optional.of(fetchingDto));
        when(setRatesRepository.save(any(SetRates.class))).thenReturn(setRates);

        // Act
        SetRates result = setRatesService.saveSetRates(setRates);

        // Assert
        assertNotNull(result);
        assertEquals(fetchingDto, result.getFetchingDto());
        assertEquals(fetchingDto.getType().getAmount(), result.getPricePerDay(), 0.01);

        long daysBetween = ChronoUnit.DAYS.between(fetchingDto.getCheckIn(), fetchingDto.getCheckOut());
        double expectedPrice = (daysBetween * setRates.getPricePerDay()) - fetchingDto.getAdvanceAmount();
        assertEquals(expectedPrice, result.getPrice(), 0.01);
    }



    @Test
    public void testSaveSetRates_MemberCodeNotFound() {
        // Arrange
        FetchingDto fetchingDto = new FetchingDto();
        fetchingDto.setMemberCode(1);
        SetRates setRates = new SetRates();
        setRates.setFetchingDto(fetchingDto);

        when(fetchingRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> setRatesService.saveSetRates(setRates));
        assertEquals("membercode not found", exception.getMessage());
    }

    @Test
    public void testSaveSetRates_InvalidDates() {
        // Arrange
        FetchingDto fetchingDto = new FetchingDto();
        fetchingDto.setMemberCode(1);
        fetchingDto.setType(Roomtype.STANDARD);
        fetchingDto.setCheckIn(LocalDate.of(2024, 9, 5));
        fetchingDto.setCheckOut(LocalDate.of(2024, 9, 1));

        SetRates setRates = new SetRates();
        setRates.setFetchingDto(fetchingDto);
        setRates.setExtensionPrice(ExtensionPrice.STANDARD);

        when(fetchingRepository.findById(1)).thenReturn(Optional.of(fetchingDto));

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> setRatesService.saveSetRates(setRates));
        assertEquals("Check-out date must be after check-in date", exception.getMessage());
    }

    @Test
    public void testGetSetRatesById_Success() throws UserException {
        // Arrange
        SetRates setRates = new SetRates();
        setRates.setId(1L);

        when(setRatesRepository.findById(1L)).thenReturn(Optional.of(setRates));

        // Act
        SetRates result = setRatesService.getSetRatesById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetSetRatesById_IdNotFound() {
        // Arrange
        when(setRatesRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> setRatesService.getSetRatesById(1L));
        assertEquals("id not found", exception.getMessage());
    }

    @Test
    public void testGetAllSetRates_Success() throws UserException {
        // Arrange
        SetRates setRates1 = new SetRates();
        SetRates setRates2 = new SetRates();

        when(setRatesRepository.findAll()).thenReturn(Arrays.asList(setRates1, setRates2));

        // Act
        List<SetRates> result = setRatesService.getAllSetRates();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllSetRates_Empty() {
        // Arrange
        when(setRatesRepository.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> setRatesService.getAllSetRates());
        assertEquals("it is empty", exception.getMessage());
    }
}





