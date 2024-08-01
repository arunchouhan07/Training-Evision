package com.ecom.service.impl;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void increaseFailedAttempt(UserDtls user) throws IOException {

    }

    @Override
    public void userAccountLock(UserDtls user) throws IOException {

    }

    @Override
    public boolean unlockAccountTimeExpired(UserDtls user) throws IOException {
        return false;
    }

    @Override
    public void resetAttempt(int userId) {

    }
}
