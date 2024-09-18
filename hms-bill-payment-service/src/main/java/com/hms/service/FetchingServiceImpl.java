package com.hms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.dto.FetchingDto;
import com.hms.repository.FetchingRepository;
@Service
public class FetchingServiceImpl {
	@Autowired
    FetchingRepository fetchingRepository;
	public FetchingDto saveFetchingDetails(FetchingDto fetchingDto) {
		return fetchingRepository.save(fetchingDto);
	}
	
	
	public void deleteFetchingDto(int id) {
		Optional<FetchingDto> byId = fetchingRepository.findById(id);
		
		if(byId.isPresent()) {
			FetchingDto fetchingDto = byId.get();
			
			fetchingRepository.delete(fetchingDto);
		}
		
	}
	
}
