package com.micro.ratingservice.services;

import com.micro.ratingservice.entity.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RatingService {
    //create rating
    Rating create(Rating rating);

    //getAllRating
    List<Rating> getRatings();

    //getRating by userId
    List<Rating> getRatingsByUserId(String userId);

    //get Rating by hotelId
    List<Rating> getRatingsByHotelId(String hotelId);

}
