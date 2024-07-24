package com.micro.userservice.services.impl;

import com.micro.userservice.entity.Hotel;
import com.micro.userservice.entity.Rating;
import com.micro.userservice.entity.User;
import com.micro.userservice.exception.ResourceNotFoundException;
import com.micro.userservice.externalService.HotelService;
import com.micro.userservice.externalService.RatingService;
import com.micro.userservice.repository.UserRepository;
import com.micro.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String id=UUID.randomUUID().toString();
        user.setUserId(id);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> users=userRepository.findAll();
        for(User user:users){
//            String url="http://RATINGSERVICE/ratings/users/"+user.getUserId();
//            Rating[] ratings=restTemplate.getForObject(url, Rating[].class);
//            List<Rating>rating=Arrays.stream(ratings).toList();
//      TODO By Using Feign Client
            //TODO Changing Array to List
            Rating[] ratings= ratingService.findRatingsByUserId(user.getUserId());
            List<Rating> rating=Arrays.stream(ratings).toList();

            rating.stream().map(r->{
//                String urlForParticularHotel="http://HOTEL-SERVICE/hotels/"+r.getHotelId();
//                Hotel h=restTemplate.getForObject(urlForParticularHotel, Hotel.class);
//      TODO By Using Feign Client
                Hotel h= hotelService.getHotel(r.getHotelId());
                r.setHotel(h);
                return r;
            }).collect(Collectors.toList());
            user.setRatings(rating);
        }
        return users;
    }

    @Override
    public User getUser(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with Given Id is not found on Server!!"+userId));

        String urlForRating="http://RATINGSERVICE/ratings/users/"+userId;
        Rating[] ratings= restTemplate.getForObject(urlForRating, Rating[].class);
        List<Rating>rating=Arrays.stream(ratings).toList();
        rating.stream().map(r->{
//            String url="http://HOTEL-SERVICE/hotels/"+r.getHotelId();
//            logger.info("URL",url);
//            Hotel hotel=restTemplate.getForObject(url, Hotel.class);
            Hotel hotel=hotelService.getHotel(r.getHotelId());

            r.setHotel(hotel);
            return r;
        }).collect(Collectors.toList());

        user.setRatings(rating);

        return  user;
    }

    @Override
    public boolean deleteUser(String userId) {
        return false;
    }

    @Override
    public User updateUser(String userId, User user) {
        return null;
    }
}
