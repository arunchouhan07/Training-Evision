package com.udemy.securityjwt.service;

import com.udemy.securityjwt.domain.RoleDto;
import com.udemy.securityjwt.domain.UserDto;

import java.util.List;


public interface UserService {
    UserDto saveUser(UserDto userDto);
    RoleDto saveRole(RoleDto roleDto);
    boolean saveRoleToUser(String username, String roleName);
    UserDto getUser(String username);
    List<UserDto> getAllUsers();
}
