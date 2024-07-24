package com.micro.hotelservice.services.impl;

import com.micro.hotelservice.entity.Hotel;
import com.micro.hotelservice.exceptions.ResourceNotFountException;
import com.micro.hotelservice.repositotries.HotelRepository;
import com.micro.hotelservice.services.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        String id= UUID.randomUUID().toString();
        hotel.setId(id);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(()->new ResourceNotFountException("Hotel Not Found with Given Id "+id));
    }
}
