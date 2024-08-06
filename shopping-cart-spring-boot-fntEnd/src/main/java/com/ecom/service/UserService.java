package com.ecom.service;

import com.ecom.model.UserDtls;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface UserService {

    UserDtls saveUser(UserDtls user, MultipartFile file) throws IOException;

    UserDtls getUserByEmail(String email);

    void increaseFailedAttempt(UserDtls user) throws IOException;

    public void resetAttempt(int userId);

    List<UserDtls> getAllUsers();

    Boolean updateUserAcountStatus(Integer id, Boolean status);

    public void accountLock(UserDtls userDtls);

    public boolean unlockAccountTimeExpire(UserDtls userDtls);

}
