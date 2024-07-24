package com.jwt.springjwt.filter;

import com.jwt.springjwt.constent.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;

public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authHeader = request.getHeader("Authorization");

        //Converting a Api_Secret_Key in String Format to SecretKey Format.
        byte[] decodedKey = Base64.getDecoder().decode(Constants.API_SECRET_KEY);
        SecretKey secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring("Bearer ".length());

            //-------> This is the another way ofo getting  token<-----------//
//          String[] authHeaderArr= authHeader.split("Bearer");
//          if(authHeaderArr.length>1 && authHeaderArr[1]!=null){
//               String token = authHeaderArr[1].trim();

              try{
                  Claims claims= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
                  request.setAttribute("userId",Integer.parseInt(claims.get("userId").toString()));
              }catch (Exception e){
                  response.sendError(HttpStatus.FORBIDDEN.value(),"invalid/expired token");
                  return;
              }
          }else{
              response.sendError(HttpStatus.FORBIDDEN.value(),"Authorize token must be Bearer [token]");
              return;
          }
        filterChain.doFilter(request, response);
    }
}
