package com.hms;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.hms.entity.Inventory;
import com.hms.exception.CustomException;
import com.hms.repo.InventoryRepository;
import com.hms.service.InventoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory;
    private Inventory updatedInventory;

    @BeforeEach
    void setUp() {
       inventory = new Inventory(1, "Item1", 100, 10);
       updatedInventory = new Inventory(1, "UpdatedItem", 150, 20);
    
    }

    @Test
    void testAddInventory() {
        when(inRepository.save(any(Inventory.class))).thenReturn(inventory);

        Inventory result = inventoryService.addInventory(inventory);

        assertNotNull(result);
        assertEquals("Item1", result.getItemName());
        verify(inRepository, times(1)).save(inventory);
    }

    @Test
    void testDisplayAllInventorys() {
        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);
        when(inRepository.findAll()).thenReturn(inventoryList);

        List<Inventory> result = inventoryService.displayAllInventorys();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Item1", result.get(0).getItemName());
        verify(inRepository, times(1)).findAll();
    }

    @Test
    void testUpdateItemSuccess() throws CustomException {
        when(inRepository.findById(1)).thenReturn(Optional.of(inventory));
        when(inRepository.save(any(Inventory.class))).thenReturn(updatedInventory);

        inventoryService.updateItem(1, updatedInventory);

        verify(inRepository, times(1)).findById(1);
        verify(inRepository, times(1)).save(updatedInventory);
    }

    

    @Test
    void testDeleteInventorySuccess() throws CustomException {
        when(inRepository.findById(1)).thenReturn(Optional.of(inventory));

        inventoryService.deleteInventory(1);

        verify(inRepository, times(1)).findById(1);
        verify(inRepository, times(1)).deleteById(1);
    }

   }

