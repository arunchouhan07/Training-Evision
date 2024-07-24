package com.micro.hotelservice.services;


import com.micro.hotelservice.entity.Hotel;

import java.util.List;

public interface HotelService {
//    create
    Hotel create(Hotel hotel);
//    getAll
    List<Hotel> getAll();

    //get single
    Hotel get(String id);
}

