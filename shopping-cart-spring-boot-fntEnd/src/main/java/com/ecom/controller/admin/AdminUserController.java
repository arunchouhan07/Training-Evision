package com.ecom.controller.admin;

import com.ecom.model.UserDtls;
import com.ecom.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDtls> allUsers = userService.getAllUsers();
        log.info("allUsers: " + allUsers);
        model.addAttribute("users", allUsers);
        return "admin/users";
    }
    
    @GetMapping("/updateSts")
    public String updateSts(@RequestParam Boolean status, @RequestParam Integer id, HttpSession session) {
        Boolean b = userService.updateUserAcountStatus(id, status);
        if (b) {
        //    session.setAttribute("succMsg", "user status updated");
        }
        else {
          //  session.setAttribute("errorMsg", "user status not updated");
        }
        return "redirect:/admin/users";
    }

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            UserDtls userByEmail = userService.getUserByEmail(email);
            model.addAttribute("user", userByEmail);
        }
    }

}
