package com.jwt.springjwt.controller;

import com.jwt.springjwt.constent.Constants;
import com.jwt.springjwt.domain.UsersDto;
import com.jwt.springjwt.domain.ValidUser;
import com.jwt.springjwt.model.Et_users;
import com.jwt.springjwt.service.UserServices;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServices userServices;

    @GetMapping()
    public String index() {
        return "Hello World";
    }

    @GetMapping("/categories")
    public String findAllCategories(HttpServletRequest request) {
        int userId=Integer.parseInt(request.getAttribute("userId").toString());
        return "Authonticated! User with "+userId;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody UsersDto usersDto) {
       UsersDto savedUser= userServices.registerUser(usersDto);
       return new ResponseEntity<>(generateJwtToken(savedUser),HttpStatus.OK);
    }

    @PostMapping("/validuser")
    public ResponseEntity<Map<String,String>> validUser(@RequestBody ValidUser validUser) {
        UsersDto usersDto=userServices.validateUser(validUser.getEmail(), validUser.getPassword());
        return new ResponseEntity<>(generateJwtToken(usersDto), HttpStatus.OK);
    }

    private Map<String,String> generateJwtToken(UsersDto usersDto){
        String token= Jwts.builder().signWith(SignatureAlgorithm.HS256,Constants.API_SECRET_KEY)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+Constants.TOKEN_VALIDITY))
                .claim("userId",usersDto.getId())
                .claim("email",usersDto.getEmail())
                .claim("firstName",usersDto.getFirstName())
                .claim("lastName",usersDto.getLastName())
                .compact();
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        return map;
    }

}
