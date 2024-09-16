package com.ecom.service.impl;

import com.ecom.entity.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;
import com.ecom.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

     @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public UserDtls saveUser(UserDtls user, String imageUrl) throws IOException {
        user.setProfileImage(imageUrl);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsEnable(true);
        user.setLockTime(null);
        user.setFailedAttempt(0);
        user.setAccountNonLocked(true);
        return userRepository.save(user);

    }

    @Override
    public UserDtls getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void increaseFailedAttempt(UserDtls user) throws IOException {
        int attempt = user.getFailedAttempt() + 1;
        user.setFailedAttempt(attempt);
        userRepository.save(user);
    }

    @Override
    public List<UserDtls> getAllUsers() {
        return userRepository.findByRole("ROLE_USER");
    }

    @Override
    public Boolean updateUserAcountStatus(Integer id, Boolean status) {
        Optional<UserDtls> userById = userRepository.findById(id);
        if(userById.isPresent()) {
            UserDtls user = userById.get();
            user.setIsEnable(status);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void accountLock(UserDtls userDtls) {
        userDtls.setAccountNonLocked(false);
        userDtls.setLockTime(new Date());
        userRepository.save(userDtls);
    }

    @Override
    public boolean unlockAccountTimeExpire(UserDtls userDtls) {
        long lockTime = userDtls.getLockTime().getTime();
        long unlockTime = lockTime+ AppConstant.UNLOCK_DURATION_TIME;
        long currentTimeMillisecond = System.currentTimeMillis();

        if(unlockTime<currentTimeMillisecond){
            userDtls.setAccountNonLocked(true);
            userDtls.setFailedAttempt(0);
            userDtls.setLockTime(null);
            userRepository.save(userDtls);
            return true;
        }
        return false;
    }

    @Override
    public Boolean sendForgotPasswordToMail(String email, HttpServletRequest request) {
        UserDtls userByEmail = userRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(userByEmail)){
            return false;
        }else{
            String resetToken = UUID.randomUUID().toString();
            userByEmail.setResetToken(resetToken);
            userRepository.save(userByEmail);

            String url = CommonUtil.generateUrl(request)+"/reset-password?token="+resetToken;

            Boolean sendMail = commonUtil.sendMail(userByEmail.getEmail(),url);
            if(sendMail ){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public UserDtls getUserByToken(String token) {
        return userRepository.findByResetToken(token);

    }

    @Override
    public int updateUser(UserDtls userDtls) {
        return userRepository.updateUserPassword(userDtls.getEmail(),userDtls.getPassword());
    }

}
