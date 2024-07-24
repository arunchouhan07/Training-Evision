package com.micro.hotelservice.controllers;

import com.micro.hotelservice.entity.Hotel;
import com.micro.hotelservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;
//    create
    @PostMapping
        public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel)
        {
         return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
        }
    //get single
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(hotelId));
    }
//    get all
    @GetMapping
        public ResponseEntity<List<Hotel>> getAll()
        {
           return ResponseEntity.ok(hotelService.getAll());
        }
}
