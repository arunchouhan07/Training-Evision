package com.jwt.springjwt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
