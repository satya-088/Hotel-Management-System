package com.hms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.entity.Staff;



public interface StaffRepository extends JpaRepository<Staff, Long>{

}
