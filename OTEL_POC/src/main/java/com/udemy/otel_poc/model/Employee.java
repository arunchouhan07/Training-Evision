package com.udemy.otel_poc.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String department;
    // Getters and setters
}