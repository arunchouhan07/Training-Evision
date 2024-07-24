package com.udemy.securityjwt.repository;

import com.udemy.securityjwt.domain.UserDto;
import com.udemy.securityjwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
        UserEntity findByUsername(String username);
}
