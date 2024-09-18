package com.hms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.entity.Inventory;



public interface InventoryRepository extends JpaRepository<Inventory, Integer>{

}
