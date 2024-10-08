package com.ecom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthSuccessHandlerImpl authSuccessHandler;

    @Autowired
    @Lazy
    private AuthFailureHandlerImpl authFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((req) -> {
                    //TODO you not set hasRole(ROLE_USER) because in springSecurity 4 it automatically add ROLE keyword
                    req.requestMatchers("/user/**").hasRole("USER");
                    req.requestMatchers("/admin/**").hasRole("ADMIN");
                    req.requestMatchers("/**").permitAll();
                }).formLogin(form -> form.loginPage("/singing")
                                //TODO the below loginProcessUrl is managed by spring security
                                .loginProcessingUrl("/login")
                                //TODO because you use email attribute(name="email") in your login.html form
                                .usernameParameter("email")
                                .successHandler(authSuccessHandler)
                                .failureHandler(authFailureHandler)
                        //.defaultSuccessUrl("/")
                        )
                .logout(LogoutConfigurer::permitAll)
                .requestCache(requestCache -> requestCache.requestCache(new HttpSessionRequestCache()))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
