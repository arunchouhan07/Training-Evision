package com.ecom.service.impl;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDtls saveUser(UserDtls user, MultipartFile file) throws IOException {
        String imageName = (!file.isEmpty() && file != null) ? file.getOriginalFilename() : "default.jpg";
        user.setProfileImage(imageName);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsEnable(true);
        user.setLockTime(null);
        user.setFailedAttempt(0);
        user.setAccountNonLocked(true);
        UserDtls savedUser = userRepository.save(user);

        if(!ObjectUtils.isEmpty(savedUser))
        {
            if(file != null && !file.isEmpty()){
                File saveFile = new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator
                        + imageName);

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        return savedUser;
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

//    @Override
//    public void userAccountLock(UserDtls user) throws IOException {
//
//    }

//    @Override
//    public boolean unlockAccountTimeExpired(UserDtls user) throws IOException {
//        return false;
//    }

    @Override
    public void resetAttempt(int userId) {

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
        long currentTimeMilisecond = System.currentTimeMillis();

        if(unlockTime<currentTimeMilisecond){
            userDtls.setAccountNonLocked(true);
            userDtls.setFailedAttempt(0);
            userDtls.setLockTime(null);
            userRepository.save(userDtls);
            return true;
        }
        return false;
    }
}
