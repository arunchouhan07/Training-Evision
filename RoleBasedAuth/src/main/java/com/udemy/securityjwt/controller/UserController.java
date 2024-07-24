package com.udemy.securityjwt.controller;

import com.udemy.securityjwt.domain.RoleDto;
import com.udemy.securityjwt.domain.UserDto;
import com.udemy.securityjwt.repository.UserRepository;
import com.udemy.securityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/save/user")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        UserDto user = userService.saveUser(userDto);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/save/role")
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleDto roleDto){
        RoleDto role = userService.saveRole(roleDto);
        return ResponseEntity.ok().body(role);
    }

    @PostMapping("/save/roletouser/{username}/{rolename}")
    public String saveRoleUser(@PathVariable String username, @PathVariable String rolename){
        boolean isAdded=userService.saveRoleToUser(username,rolename);
        return isAdded?"Role Add to User":"Role Not Add to User";
    }


}
