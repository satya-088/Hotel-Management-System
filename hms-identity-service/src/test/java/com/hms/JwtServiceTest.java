package com.hms;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hms.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void testGenerateToken() {
        String username = "testUser";
        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertTrue(token.startsWith("ey"));
    }

    @Test
    void testValidateToken() {
        String username = "testUser";
        String token = jwtService.generateToken(username);

        // This should not throw an exception
        assertDoesNotThrow(() -> jwtService.validateToken(token));
    }

 

    @Test
    void testValidateInvalidToken() {
        String invalidToken = "invalid.token.here";

        // This should throw a JwtException
        assertThrows(JwtException.class, () -> jwtService.validateToken(invalidToken));
    }

    @Test
    void testGetUsernameFromToken() {
        String username = "testUser";
        String token = jwtService.generateToken(username);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtService.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(username, claims.getSubject());
    }
    
//    @Test
//    void testValidateTokenWithExpiredToken() {
//        String username = "testUser";
//        String token = jwtService.generateToken(username);
//        
//        // Simulate token expiration
//        try {
//            Thread.sleep(1000 * 60 * 31); // Sleep for 31 minutes
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        // This should throw an ExpiredJwtException
//        assertThrows(ExpiredJwtException.class, () -> jwtService.validateToken(token));
//    }
//    @Test
//    void testValidateTokenWithExpiredToken() {
//        String username = "testUser";
//        String token = jwtService.generateToken(username);
//
//        // Manually expire the token by checking the expiration logic
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(jwtService.getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        // Set the expiration to a past date
//        claims.setExpiration(new Date(System.currentTimeMillis() - 1000)); // 1 second ago
//
//        // Attempting to validate the expired token
//        assertThrows(ExpiredJwtException.class, () -> jwtService.validateToken(token));
//    }
    @Test
    void testValidateTokenWithExpiredToken() {
        String username = "testUser";
        
        // Manually create an expired token
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 31)) // Issued 31 minutes ago
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 1)) // Expired 1 minute ago
                .signWith(jwtService.getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        // This should throw an ExpiredJwtException
        assertThrows(ExpiredJwtException.class, () -> jwtService.validateToken(token));
    }


}
