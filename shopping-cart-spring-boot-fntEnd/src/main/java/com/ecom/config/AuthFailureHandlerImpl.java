package com.ecom.config;

import com.ecom.entity.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Component
@Slf4j
public class AuthFailureHandlerImpl implements AuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("Failed to authenticate user");
        String email = request.getParameter("email");
        UserDtls user = userRepository.findByEmail(email);

        if (user != null && user.getIsEnable()) {
            if (user.getAccountNonLocked() || userService.unlockAccountTimeExpire(user)) {
                log.info("User account is non-locked");
                if (user.getFailedAttempt() < AppConstant.ATTEMPT - 1) {
                    userService.increaseFailedAttempt(user);
                    log.info("Failed attempt increased");
                    exception=new LockedException("Invalid username or password");
                } else {
                    userService.accountLock(user);
                    log.info("User account is locked due to 3 failed attempts");
                    exception = new LockedException("Your account is locked for 1 hour");
                }
            }else{
                exception=new LockedException("User Account is locked Please try after some time");
            }
//            else {
//                log.info("User account is locked");
//                if (userService.unlockAccountTimeExpire(user)) {
//                    log.info("User lock time expired. You can now login");
//                    exception = new LockedException("Your account is unlocked! Try to log in");
//                } else {
//                    log.info("User account is already locked! Please try after some time");
//                    exception = new LockedException("Your account is locked! Please try after some time");
//                }
//            }
        } else if (user != null) {
            log.info("User account is inactive");
            exception = new LockedException("Your account is inactive");
        }

        request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",exception);
        response.sendRedirect("/login?error=true");
    }
}
