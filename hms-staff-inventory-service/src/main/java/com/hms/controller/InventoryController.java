package com.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.entity.Inventory;
import com.hms.exception.CustomException;
import com.hms.service.InventoryService;



@RestController
@RequestMapping("/inventory")
public class InventoryController {
	@Autowired
	InventoryService inventoryService;
	
	@PostMapping("/add")
	@PreAuthorize("hasAnyAuthority('manager','owner')")
	public Inventory add(@RequestBody Inventory inv) {
		return inventoryService.addInventory(inv);		
	}
	@GetMapping("/display")
	@PreAuthorize("hasAnyAuthority('manager','owner')")
	public List<Inventory> displayAllInventorys(){
		return inventoryService.displayAllInventorys();
		
	}
	@PutMapping("/update/{id}")
	@PreAuthorize("hasAnyAuthority('manager','owner')")
	public void updateItem (@PathVariable int id,@RequestBody Inventory inventory) throws CustomException {
		inventoryService.updateItem(id,inventory);
	}
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('manager','owner')")
	public void deleteInventory(@PathVariable int id) throws CustomException {
		
		inventoryService.deleteInventory(id);
	}
}
