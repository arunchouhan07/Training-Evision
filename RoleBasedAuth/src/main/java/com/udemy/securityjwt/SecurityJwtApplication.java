package com.udemy.securityjwt;

import com.udemy.securityjwt.domain.RoleDto;
import com.udemy.securityjwt.domain.UserDto;
import com.udemy.securityjwt.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    CommandLineRunner commandLineRunner(UserService userService) {
//        return args -> {
//            userService.saveRole(new RoleDto(null,"ROLE_ADMIN"));
//            userService.saveRole(new RoleDto(null,"ROLE_MODERATOR"));
//            userService.saveRole(new RoleDto(null,"ROLE_SUPER_ADMIN"));
//            userService.saveRole(new RoleDto(null,"ROLE_MANAGER"));
//
//            userService.saveUser(new UserDto(null,"nanu","nanuchouhan","nanu2353",new ArrayList<>()));
//            userService.saveUser(new UserDto(null,"shyam","shyamkumar","shyam2341",new ArrayList<>()));
//            userService.saveUser(new UserDto(null,"shubham","shubhambhagore","shubham23432",new ArrayList<>()));
//            userService.saveUser(new UserDto(null,"anil","anilghanti","anil2423",new ArrayList<>()));
//            userService.saveUser(new UserDto(null,"mansvi","manshimishra","mansvi24134",new ArrayList<>()));
////
//            userService.saveRoleToUser("nanuchouhan","ROLE_ADMIN");
////            userService.saveRoleToUser("shyamkumar","ROLE_MODERATOR");
////            userService.saveRoleToUser("manshimishra","ROLE_SUPER_ADMIN");
////            userService.saveRoleToUser("anilghanti","ROLE_MANAGER");
//
//      };
//    }
}
