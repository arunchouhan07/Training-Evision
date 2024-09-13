package com.ecom.config;

import com.ecom.entity.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Service
public class AuthSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    @Lazy
            private UserService userService;
    @Autowired
            private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> roles = AuthorityUtils.authorityListToSet(authorities);
        CustomUser principal = (CustomUser) authentication.getPrincipal();

        UserDtls userByEmail = userService.getUserByEmail(principal.getUsername());
        userByEmail.setFailedAttempt(0);
        userByEmail.setAccountNonLocked(true);
        userRepository.save(userByEmail);


//        if(roles.contains("ROLE_ADMIN")) {
//            response.sendRedirect("/admin");
//        }else{
//            response.sendRedirect("/");
//        }


        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        String targetUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : "/";

        if(roles.contains("ROLE_ADMIN")){
            if(targetUrl == "/"){
                targetUrl = "/admin";
            }
        }
        response.sendRedirect(targetUrl);
    }
}
