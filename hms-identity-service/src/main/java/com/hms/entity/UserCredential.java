package com.hms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userlogin")
public class UserCredential {

    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,nullable = false)
    @NotNull(message = "username should be mandatory")
    private String username;
    private String email;
    @Size(min = 7,message = "password must be 7 characters")
    private String password;
    @NotNull(message = "role should be mandatory")
    private String role;
    
    
    
    

    
}
