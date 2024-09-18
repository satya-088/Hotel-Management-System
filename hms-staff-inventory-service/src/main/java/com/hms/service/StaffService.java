package com.hms.service;

import java.util.List;

import com.hms.entity.Staff;
import com.hms.exception.CustomException;



public interface StaffService {
	Staff addStaff(Staff staff);
	List<Staff> findAllStaffDetails();
	Staff displayStaffDetails(long sid) throws CustomException ;
	void updateSalaryById(long sid,float salary) throws CustomException;
	void deleteStaff(long sid) throws CustomException;
}
