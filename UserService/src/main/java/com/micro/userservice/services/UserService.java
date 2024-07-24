package com.micro.userservice.services;

import com.micro.userservice.entity.User;

import java.util.List;

public interface UserService {
    //user operations

    //create
    User saveUser(User user);

    //getAllUser
    List<User> getAllUser();

    //get single user of given userId
    User getUser(String userId);

    //Delete User By UserID
    boolean deleteUser(String userId);

    //Update User by UserID
    User updateUser(String userId,User user);
}
