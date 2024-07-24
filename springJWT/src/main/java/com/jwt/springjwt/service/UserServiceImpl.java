package com.jwt.springjwt.service;

import com.jwt.springjwt.domain.UsersDto;
import com.jwt.springjwt.exception.EtAuthException;
import com.jwt.springjwt.mapper.Mapper;
import com.jwt.springjwt.model.Et_users;
import com.jwt.springjwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserServices {


    private UserRepository userRepository;

    @Override
    public UsersDto validateUser(String email, String password) throws EtAuthException {
        Et_users etUsers=userRepository.findByEmail(email);
        UsersDto usersDto=new UsersDto();
        if(etUsers!=null && etUsers.getPassword().equals(password)){
            usersDto=Mapper.mapToUserDto(etUsers);
        }
        return usersDto;
    }

    @Override
    public UsersDto registerUser(UsersDto usersDto) {

        Et_users getUser=userRepository.findByEmail(usersDto.getEmail());
        if(getUser!=null){
            throw  new EtAuthException("Email Already Exists");
        }

        Et_users save = Mapper.mapToEtUser(usersDto);

        save = userRepository.save(save);

        return new UsersDto(save.getUser_id(),save.getFirst_name(), save.getLast_name(), save.getEmail(), save.getPassword());
    }
}
