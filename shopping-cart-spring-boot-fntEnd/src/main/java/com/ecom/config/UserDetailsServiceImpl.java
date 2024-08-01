package com.ecom.config;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Loading user by email: {}", email);

        UserDtls userByEmail = userRepository.findByEmail(email);
        if (userByEmail == null) {
            logger.error("User not found with username: {}", email);
            throw new UsernameNotFoundException("user not found with username: " + email);
        }
        logger.info("User found: {}", userByEmail);
        return new CustomUser(userByEmail);
    }
}
