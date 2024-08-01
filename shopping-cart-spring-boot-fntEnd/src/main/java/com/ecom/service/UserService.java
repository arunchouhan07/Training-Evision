package com.ecom.service;

import com.ecom.model.UserDtls;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    UserDtls saveUser(UserDtls user, MultipartFile file) throws IOException;

    void increaseFailedAttempt(UserDtls user) throws IOException;

    void userAccountLock(UserDtls user) throws IOException;

    boolean unlockAccountTimeExpired(UserDtls user) throws IOException;

    public void resetAttempt(int userId);


}
