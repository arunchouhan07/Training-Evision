package com.udemy.securityjwt.repository;

import com.udemy.securityjwt.domain.RoleDto;
import com.udemy.securityjwt.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
