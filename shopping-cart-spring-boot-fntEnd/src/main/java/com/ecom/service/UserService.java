package com.ecom.service;

import com.ecom.model.UserDtls;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    public UserDtls saveUser(UserDtls user, MultipartFile file) throws IOException;

}
