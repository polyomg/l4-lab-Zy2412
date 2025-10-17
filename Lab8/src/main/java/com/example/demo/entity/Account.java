package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Accounts")
@Data
public class Account implements Serializable {
    @Id
    private String username;
    private String password;
    private String fullname;
    private String email;
    private boolean admin = false;
}