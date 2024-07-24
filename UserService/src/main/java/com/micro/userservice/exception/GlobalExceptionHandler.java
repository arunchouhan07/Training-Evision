package com.micro.userservice.exception;

import com.micro.userservice.payload.ApiHandler;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final RestTemplateAutoConfiguration restTemplateAutoConfiguration;

    public GlobalExceptionHandler(RestTemplateAutoConfiguration restTemplateAutoConfiguration) {
        this.restTemplateAutoConfiguration = restTemplateAutoConfiguration;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<ApiHandler> handelResourceNotFoundException(ResourceNotFoundException e) {
         ApiHandler apiHandler = ApiHandler.builder().message(e.getMessage()).success(false).status(HttpStatus.NOT_FOUND).build();
         return new ResponseEntity<>(apiHandler,HttpStatus.NOT_FOUND);
     }

//    public ResponseEntity<Map<String, Object>> resourceNotFound(ResourceNotFoundException e) {
//         Map<String, Object> body = new HashMap<>();
//         body.put("message", e.getMessage());
//         body.put("success", false);
//         body.put("status", HttpStatus.NOT_FOUND);
//         return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//     }
}
