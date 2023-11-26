package com.ginnyci.ginnyapi.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Dog {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
