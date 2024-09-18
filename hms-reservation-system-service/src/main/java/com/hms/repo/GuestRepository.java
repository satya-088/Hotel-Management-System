package com.hms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer>{

}
