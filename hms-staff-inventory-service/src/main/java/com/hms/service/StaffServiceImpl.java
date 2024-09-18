package com.hms.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.hms.entity.Staff;
import com.hms.exception.CustomException;
import com.hms.repo.StaffRepository;




@Component
public class StaffServiceImpl implements StaffService{
	@Autowired
	StaffRepository stRepository;
	private Logger log=LoggerFactory.getLogger(StaffServiceImpl.class);
	
	@Override
	public Staff addStaff(Staff staff) {
		log.info("Attempting to add staff: {}", staff);
		Staff save = stRepository.save(staff);
		return save;
	}
	@Override
	public List<Staff> findAllStaffDetails() {
		log.info("Fetching all staff details");
		return stRepository.findAll();
	}

	@Override
	public Staff displayStaffDetails(long sid) throws CustomException {
		 log.info("Fetching staff details for id: {}", sid);
		return stRepository.findById(sid).get();
	}
	@Override
	public void updateSalaryById(long sid, float salary) throws CustomException{
		log.info("Attempting to update salary for staff with id: {} to {}", sid, salary);
		Optional<Staff> byId = stRepository.findById(sid);
		
		if(byId.isPresent()) {
			Staff staff = byId.get();
			staff.setSalary(salary);
			
			stRepository.save(staff);
		}else {
			throw new CustomException("please enter valid id");
		}
		
		
	}
	@Override
	public void deleteStaff(long sid) throws CustomException{
        log.info("Attempting to delete staff with id: {}", sid);
		Optional<Staff> byId = stRepository.findById(sid);
		if(byId.isPresent()) {
			Staff staff=byId.get();
			staff.setCode(sid);
			stRepository.deleteById(sid);
		}else {
			throw new CustomException("please enter valid id");
		}
	}
	
	

}
