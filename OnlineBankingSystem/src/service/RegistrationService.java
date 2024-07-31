package service;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class RegistrationService {
    private Map<String, User> userMap = new HashMap<>();

    public boolean registerUser(String username, String password, String email, String mobileNumber, String dob, String gender) {
        if (userMap.containsKey(username)) {
            return false;
        }
        User user = new User(username, password, email, mobileNumber, dob, gender);
        userMap.put(username, user);
        return true;
    }

    public User loginUser(String username, String password) {
        User user = userMap.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }

}
