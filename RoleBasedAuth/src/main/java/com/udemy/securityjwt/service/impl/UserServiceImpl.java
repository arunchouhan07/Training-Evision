package com.udemy.securityjwt.service.impl;

import com.udemy.securityjwt.domain.RoleDto;
import com.udemy.securityjwt.domain.UserDto;
import com.udemy.securityjwt.entity.RoleEntity;
import com.udemy.securityjwt.entity.UserEntity;
import com.udemy.securityjwt.exception.RoleNotAddedException;
import com.udemy.securityjwt.exception.UserNotFoundException;
import com.udemy.securityjwt.mapper.Mapper;
import com.udemy.securityjwt.repository.RoleRepository;
import com.udemy.securityjwt.repository.UserRepository;
import com.udemy.securityjwt.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        log.info("Saving user with userName: {}", userDto.getName());

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
     //   userEntity.setRoleName(userDto.getRoleName());
    //    userEntity.setRole(userDto.getRole());

       ///  Convert RoleDto objects to RoleEntity objects
        List<RoleEntity> roles = userDto.getRole()
                .stream()
                .map(roleDto -> {
                    // Assuming RoleDto has a "name" field
                  RoleEntity s=roleRepository.findByName(roleDto.getName());
                    return s;
                })
                .collect(Collectors.toList());
        userEntity.setRoleName(roles);

        UserEntity savedUser = userRepository.save(userEntity);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        log.info("Saving role with roleName: {}", roleDto.getName());
        if(roleRepository.findByName(roleDto.getName())==null){
            throw new RoleNotAddedException("Role Already Present");
        }

        RoleEntity save=roleRepository.save(modelMapper.map(roleDto, RoleEntity.class));

        return modelMapper.map(save, RoleDto.class);
    }

    @Override
    public boolean saveRoleToUser(String username, String roleName) {
        log.info("Saving role {} to user {}", roleName,username);
        UserEntity userEntity=userRepository.findByUsername(username);
        RoleEntity roleEntity=roleRepository.findByName(roleName);
        if(userEntity!=null) {
            userEntity.getRoleName().add(roleEntity);
            userRepository.save(userEntity);
            return true;
        }else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public UserDto getUser(String username) {
        log.info("Getting user by {}", username);
        try{
            UserEntity findUser=userRepository.findByUsername(username);
            if(findUser!=null) {
                return Mapper.convertEntityToDto(findUser);
            }
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return new UserDto();
    }

    @Override
    public List<UserDto> getAllUsers() {
       List<UserEntity>getAllUser=userRepository.findAll();
       List<UserDto> userDto=new ArrayList<>();
       for(UserEntity userEntity:getAllUser) {
           userDto.add(modelMapper.map(userEntity,UserDto.class));
       }
       return userDto;
    }
}
