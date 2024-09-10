package com.ecom.service;

import com.ecom.entity.UserDtls;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDtls saveUser(UserDtls user, MultipartFile file) throws IOException;

    UserDtls getUserByEmail(String email);

    void increaseFailedAttempt(UserDtls user) throws IOException;

    List<UserDtls> getAllUsers();

    Boolean updateUserAcountStatus(Integer id, Boolean status);

    public void accountLock(UserDtls userDtls);

    public boolean unlockAccountTimeExpire(UserDtls userDtls);

    public Boolean sendForgotPasswordToMail(String email, HttpServletRequest request);

    UserDtls getUserByToken(String token);

    public int updateUser(UserDtls userDtls);
}
