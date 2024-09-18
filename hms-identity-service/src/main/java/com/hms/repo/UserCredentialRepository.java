package com.hms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.entity.UserCredential;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByUsername(String username);
}
