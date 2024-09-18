package com.hms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long>{
	Optional<Bill> findByBillNumber(Long billNumber);

}
