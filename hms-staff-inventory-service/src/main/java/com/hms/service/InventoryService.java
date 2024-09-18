package com.hms.service;

import java.util.List;

import com.hms.entity.Inventory;
import com.hms.exception.CustomException;



public interface InventoryService {
	Inventory addInventory(Inventory inventory);
	List<Inventory> displayAllInventorys();
	void updateItem (int id,Inventory inventory) throws CustomException;
	void deleteInventory(int id)throws CustomException;
}
