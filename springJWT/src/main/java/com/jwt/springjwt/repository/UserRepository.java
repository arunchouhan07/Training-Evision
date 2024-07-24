package com.jwt.springjwt.repository;

import com.jwt.springjwt.domain.UsersDto;
import com.jwt.springjwt.model.Et_users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Et_users,Integer>{
    Et_users findByEmail(String email);
}
