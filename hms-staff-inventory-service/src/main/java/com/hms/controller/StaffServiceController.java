package com.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.entity.Staff;
import com.hms.exception.CustomException;
import com.hms.service.StaffService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("staff")
public class StaffServiceController {
	@Autowired
	StaffService stService;
	
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('owner')")
	public ResponseEntity<Staff> add(@Valid @RequestBody Staff s) {
		
		Staff staff = stService.addStaff(s);
		return new ResponseEntity<Staff>(staff,HttpStatus.OK);
	}
	@GetMapping("/all")
	@PreAuthorize("hasAnyAuthority('manager','owner')")
	public List<Staff> findAllStaffDetails(){
		return stService.findAllStaffDetails();
	}

	@GetMapping("/staff/{sid}")
	@PreAuthorize("hasAnyAuthority('manager','owner')")
	public ResponseEntity<Staff> display(@PathVariable long sid) throws CustomException {
		Staff staff = stService.displayStaffDetails(sid);
		ResponseEntity<Staff> rs = new ResponseEntity<Staff>(staff, HttpStatus.OK);
		return rs;
	}
	@PutMapping("/updatesalary/{sid}/{salary}")
	@PreAuthorize("hasAnyAuthority('manager','owner')")
	public void updateSalary(@PathVariable long sid,@PathVariable float salary) throws CustomException {
		stService.updateSalaryById(sid, salary);
	}
	@DeleteMapping("/delete/{sid}")
	@PreAuthorize("hasAnyAuthority('manager','owner')")
	public void delete(@PathVariable long sid) throws CustomException {
		stService.deleteStaff(sid);
	}
	
}
