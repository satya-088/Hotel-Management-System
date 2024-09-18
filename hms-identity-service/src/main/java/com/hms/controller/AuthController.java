package com.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;


import com.hms.dto.AuthRequest;
import com.hms.dto.ResponseDto;
import com.hms.entity.UserCredential;
import com.hms.exception.UserException;
import com.hms.repo.UserCredentialRepository;
import com.hms.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;
    @Autowired
    private UserCredentialRepository userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@Valid @RequestBody UserCredential user) throws UserException {
        return service.saveUser(user);
    }
    @PostMapping("/login")
    public ResponseDto getToken(@Valid @RequestBody AuthRequest authRequest) throws UserException {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
 
            if (authenticate.isAuthenticated()) {
                String token = service.generateToken(authRequest.getUsername(), authRequest.getPassword());
                UserCredential user = userRepo.findByUsername(authRequest.getUsername())
                        .orElseThrow(() -> new UserException("User not found"));
 
                ResponseDto resDto = new ResponseDto();
                resDto.setToken(token);
                resDto.setRole(user.getRole());
                return resDto;
            } else {
                throw new UserException("Invalid access");
            }
        } catch (AuthenticationException e) {
            throw new UserException("Invalid username or password");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) throws UserException {
        service.validateToken(token);
        return "Token is valid";
    }
}
