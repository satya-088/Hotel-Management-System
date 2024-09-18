package com.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.entity.UserCredential;
import com.hms.exception.UserException;
import com.hms.repo.UserCredentialRepository;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) throws UserException {
        if (repository.findByUsername(credential.getUsername()).isPresent()) {
            throw new UserException("User already exists with the same username");
        }
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "User added to the system";
    }
 
    public String generateToken(String username, String password) throws UserException {
        UserCredential user = repository.findByUsername(username)
                .orElseThrow(() -> new UserException("User not found"));
 
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException("Invalid password");
        }
 
        return jwtService.generateToken(username);
    }

 
    public void validateToken(String token) throws UserException {
        try {
            jwtService.validateToken(token);
        } catch (Exception e) {
            throw new UserException("Invalid token");
        }
    }

}
