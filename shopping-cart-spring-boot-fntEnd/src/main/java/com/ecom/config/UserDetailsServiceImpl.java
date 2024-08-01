package com.ecom.config;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDtls userByEmail = userRepository.findByEmail(username);
        if (userByEmail == null) {
            throw new UsernameNotFoundException("user not found with username: " + username);
        }
        return new CustomUser(userByEmail);
    }
}
