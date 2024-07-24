package com.udemy.securityjwt.domain;

import com.udemy.securityjwt.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String password;
    private List<RoleDto> role =new ArrayList<>();
}
