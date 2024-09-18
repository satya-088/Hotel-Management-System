package com.hms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.dto.FetchingDto;

public interface FetchingRepository extends JpaRepository<FetchingDto, Integer> {
	
	Optional<FetchingDto> findByMemberCode(int code);
	


}
