package com.hms;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.hms.entity.Staff;
import com.hms.exception.CustomException;
import com.hms.repo.StaffRepository;
import com.hms.service.StaffServiceImpl;

@SpringBootTest
public class StaffServiceImplTest {

    @Mock
    private StaffRepository stRepository;

    @InjectMocks
    private StaffServiceImpl staffImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStaff() {
        Staff staff = new Staff();
        staff.setCode(1L);
        when(stRepository.save(staff)).thenReturn(staff);

        Staff result = staffImpl.addStaff(staff);

        assertNotNull(result);
        assertEquals(staff, result);
        verify(stRepository, times(1)).save(staff);
    }

    @Test
    void testFindAllStaffDetails() {
        List<Staff> staffList = List.of(new Staff(), new Staff());
        when(stRepository.findAll()).thenReturn(staffList);

        List<Staff> result = staffImpl.findAllStaffDetails();

        assertNotNull(result);
        assertEquals(staffList.size(), result.size());
        verify(stRepository, times(1)).findAll();
    }

    @Test
    void testDisplayStaffDetails() throws CustomException {
        Staff staff = new Staff();
        staff.setCode(1L);
        when(stRepository.findById(1L)).thenReturn(Optional.of(staff));

        Staff result = staffImpl.displayStaffDetails(1L);

        assertNotNull(result);
        assertEquals(staff, result);
        verify(stRepository, times(1)).findById(1L);
    }



    @Test
    void testUpdateSalaryById() throws CustomException {
        Staff staff = new Staff();
        staff.setCode(1L);
        when(stRepository.findById(1L)).thenReturn(Optional.of(staff));

        staffImpl.updateSalaryById(1L, 5000F);

        assertEquals(5000F, staff.getSalary());
        verify(stRepository, times(1)).findById(1L);
        verify(stRepository, times(1)).save(staff);
    }

  
    @Test
    void testDeleteStaff() throws CustomException {
        Staff staff = new Staff();
        staff.setCode(1L);
        when(stRepository.findById(1L)).thenReturn(Optional.of(staff));

        staffImpl.deleteStaff(1L);

        verify(stRepository, times(1)).deleteById(1L);
    }

   
}

