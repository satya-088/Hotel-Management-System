package com.hms.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hms.entity.Inventory;
import com.hms.entity.Staff;
import com.hms.exception.CustomException;
import com.hms.repo.InventoryRepository;


@Component
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	InventoryRepository inRepository;
	private Logger log=LoggerFactory.getLogger(InventoryServiceImpl.class);
	@Override
	public Inventory addInventory(Inventory inventory) {
		 log.info("Attempting to add inventory: {}", inventory);
		return inRepository.save(inventory);
	}
	@Override
	public List<Inventory> displayAllInventorys() {
		log.info("Fetching all inventory items");
		return inRepository.findAll();
	}
	@Override
	public void updateItem(int id,Inventory inventory)throws CustomException {
		log.info("Attempting to update inventory with id: {} with new details: {}", id, inventory);
		Optional<Inventory> byId = inRepository.findById(id);
		if(byId.isPresent()) {
			Inventory inv=byId.get();
			//inventory.setId(inv.getId());
			inv.setItemName(inventory.getItemName());
			inv.setItemPrice(inventory.getItemPrice());
			inv.setQuantity(inventory.getQuantity());
			
			
			inRepository.save(inv);
		}else {
			throw new CustomException("please enter valid id");
		}
		
	}

		
	@Override
	public void deleteInventory(int id) throws CustomException {
		log.info("Attempting to delete inventory with id: {}", id);
		Optional<Inventory> byId = inRepository.findById(id);
		if(byId.isPresent()) {
			Inventory inv=byId.get();
			inv.setId(id);
			inRepository.deleteById(id);
		}else {
			throw new CustomException("please enter valid id");
		}
			
		
	}


}
