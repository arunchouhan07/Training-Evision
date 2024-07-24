package com.jwt.springjwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import io.jsonwebtoken.security.*;

@Service
public class JwtService {
    private final String SECRET_KEY="d773291445eeceada53382e2968263d8147ba172361e95ea9e15462f82d4ad7e";

    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSinginKey())
                .build().parseSignedClaims(token)
                .getPayload();
    }

//    public String generateToken(UserClass userClass){
//        String token= Jwts
//                .builder()
//                .subject(userClass.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
//                .signWith(getSinginKey())
//                .compact();
//        return token;
//    }

    private SecretKey getSinginKey() {
        byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
