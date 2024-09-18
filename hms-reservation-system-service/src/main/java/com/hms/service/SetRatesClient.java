package com.hms.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hms.entity.Reservation;

import feign.Headers;

//@FeignClient(name = "payment-details",url = "http://10.77.8.111:8080/")
@Service
@FeignClient(name = "payment-details")
public interface SetRatesClient {
	
	@PostMapping("/bills/fetching")
	@Headers("Authorization: Bearer {token}")
	public Reservation saveGuestDetails(@RequestBody Reservation reservation);
	
	
	@GetMapping("bills/deleteFetchingDto/{id}")
	@Headers("Authorization: Bearer {token}")
    public void deleteFetchingDto(@PathVariable int id);
	
	 @GetMapping("/bills/get")
	 @Headers("Authorization: Bearer {token}")
		//@PreAuthorize("hasAuthority('receptionist')")
	    public String getMessage();

}
