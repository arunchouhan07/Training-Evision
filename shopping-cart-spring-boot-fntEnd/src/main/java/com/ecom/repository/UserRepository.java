package com.ecom.repository;

import com.ecom.entity.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {
    UserDtls findByEmail(String email);

    List<UserDtls> findByRole(String role);

    UserDtls findByResetToken(String token);


    @Transactional
    @Modifying
    @Query("update UserDtls u set u.password = :password where u.email = :email")
    int updateUserPassword(@Param("email") String email,@Param("password") String password);
}
