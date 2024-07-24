package com.jwt.springjwt.mapper;

import com.jwt.springjwt.domain.UsersDto;
import com.jwt.springjwt.model.Et_users;

public class Mapper {
    public static UsersDto mapToUserDto(Et_users etUsers){
        return new UsersDto(etUsers.getUser_id(), etUsers.getFirst_name(), etUsers.getLast_name(), etUsers.getEmail(), etUsers.getPassword());
    }
    public static Et_users mapToEtUser(UsersDto usersDto){
        return new Et_users(usersDto.getId(), usersDto.getFirstName(), usersDto.getLastName(), usersDto.getEmail(), usersDto.getPassword());
    }
}
