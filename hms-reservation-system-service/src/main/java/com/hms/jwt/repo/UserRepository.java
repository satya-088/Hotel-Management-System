package com.hms.jwt.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.hms.jwt.model.User;



public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}