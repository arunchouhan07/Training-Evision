package com.micro.userservice.controller;

import com.micro.userservice.entity.User;
import com.micro.userservice.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    //User Create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    //Get Single User
    @GetMapping("/{userid}")

    //TODO By using Resilience4J
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable("userid") String userid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userid));
    }

    //Get all Users
    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    // Creating Fallback Method for Circuit Breaker
    public ResponseEntity<User> ratingHotelFallback(String userid, Exception e) {
        log.info("Fallback is executed because of an error", e.getMessage());
        User user = User.builder()
                .name("Dummy")
                .email("Dummy@dummy.com")
                .about("This is Dummy Data Because Fallback Method is Called")
                .userId("00000000")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
