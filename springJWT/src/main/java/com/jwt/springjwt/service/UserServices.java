package com.jwt.springjwt.service;

import com.jwt.springjwt.domain.UsersDto;
import com.jwt.springjwt.exception.EtAuthException;
import com.jwt.springjwt.model.Et_users;

public interface UserServices {
    UsersDto validateUser(String email, String password) throws EtAuthException;

    UsersDto registerUser(UsersDto users)throws EtAuthException;
}
