package com.ecom.config;

import com.ecom.entity.UserDtls;
import com.ecom.service.UserService;
import com.ecom.service.impl.UserServiceImpl;
import com.ecom.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class CustomUser implements UserDetails {

    UserService userService=new UserServiceImpl();

    private static final Logger logger = LoggerFactory.getLogger(CustomUser.class);

    private UserDtls userDtls;

    public CustomUser(UserDtls userDtls) {
        super();
        this.userDtls = userDtls;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());

        String role=userDtls.getRole();
        logger.info("User role: {}", role);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userDtls.getRole());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return userDtls.getPassword();
    }

    @Override
    public String getUsername() {
        return userDtls.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        System.out.println(userDtls.getLockTime().getTime());
//        System.out.println(AppConstant.UNLOCK_DURATION_TIME);
//        System.out.println(userDtls.getLockTime().getTime()+AppConstant.UNLOCK_DURATION_TIME);
//        System.out.println(System.currentTimeMillis());
//        System.out.println(userDtls.getAccountNonLocked());
//        System.out.println(System.currentTimeMillis() > userDtls.getLockTime().getTime()+(AppConstant.UNLOCK_DURATION_TIME));
//        System.out.println(userService.unlockAccountTimeExpire(userDtls));
//        return userDtls.getAccountNonLocked() || userService.unlockAccountTimeExpire(userDtls);
          return userDtls.getAccountNonLocked() || (System.currentTimeMillis() > (userDtls.getLockTime().getTime()+AppConstant.UNLOCK_DURATION_TIME)*10);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userDtls.getIsEnable();
    }
}
