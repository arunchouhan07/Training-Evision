package com.udemy.securityjwt.mapper;

import com.udemy.securityjwt.domain.RoleDto;
import com.udemy.securityjwt.domain.UserDto;
import com.udemy.securityjwt.entity.RoleEntity;
import com.udemy.securityjwt.entity.UserEntity;
import com.udemy.securityjwt.repository.RoleRepository;
import com.udemy.securityjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    @Autowired
    private static RoleRepository roleRepository;

    public static UserEntity convertDtoToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());

        // Convert RoleDto objects to RoleEntity objects
        List<RoleEntity> roles = userDto.getRole()
                .stream()
                .map(roleDto -> {
                    // Assuming RoleDto has a "name" field
                    RoleEntity roleEntity = roleRepository.findByName(roleDto.getName());
                    return roleEntity;
                })
                .collect(Collectors.toList());
        userEntity.setRoleName(roles);
        return userEntity;
    }

    public static UserDto convertEntityToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setUsername(userEntity.getUsername());
        userDto.setPassword(userEntity.getPassword());

        // Convert RoleEntity objects to RoleDto objects (assuming RoleDto exists)
        List<RoleDto> roleDtos = userEntity.getRoleName()
                .stream()
                .map(roleEntity -> {
                  return new RoleDto(userEntity.getId(), roleEntity.getName());
                })
                .collect(Collectors.toList());
        userDto.setRole(roleDtos);
        return userDto;
    }
}
