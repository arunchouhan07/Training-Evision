package com.ecom.controller;

import com.ecom.entity.Category;
import com.ecom.entity.Product;
import com.ecom.entity.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/singing")
    public String login() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login1() {
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgetPassword() {
        logger.info("forget-password method called");
        return "forgot_password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, HttpServletRequest request) {
        logger.info("forgot-password from post called method called");

        logger.info("Mail is "+email);
        Boolean b = userService.sendForgotPasswordToMail(email,request);
        if (!b) {
            return "redirect:/forgot-password";
        }

        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam String token, Model model) {
          UserDtls userByToken = userService.getUserByToken(token);
        if(userByToken == null) {
               model.addAttribute("msg", "Your link is invalid or expired !!");
            return "message";
        }
        model.addAttribute("token",token);
        return "reset_password";

    }

    @PostMapping("/reset-password")
    public String resetPasswordFromMail(@RequestParam String token, @RequestParam String password, Model model){
        logger.info("reset-password method called from mail");
        UserDtls userByToken = userService.getUserByToken(token);
        if(userByToken == null) {
            logger.info("User not found from token");
            model.addAttribute("msg", "Your link is invalid or expired !!");
            return "message";
        }else {
            logger.info("user found from token");
            userByToken.setPassword(passwordEncoder.encode(password));
            userByToken.setResetToken(null);
            int updateUserPassword = userService.updateUser(userByToken);
            if (updateUserPassword!=1){
                logger.info("User Password Not Updated");
                model.addAttribute("msg","Password Not Reset");
                return "redirect:/forgot-password";
            }else{
                logger.info("User Password Updated");
                model.addAttribute("msg","Password Reset Successful");
            }
            return "message";
        }
    }

//    @PostMapping("/loginByEmailAndPassword")
//    public String loginSuccess(){
//        //TODO default login is manage by spring security bot by the controller that's why After the login the user redirect to base url not to the user/home.html
//        logger.info("Attempting to log in user with email: {}");
//     //   System.out.println(userDtls.toString());
//        return "/user/home";
//    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/products")
    public String products(Model model, @RequestParam(value = "category", defaultValue = "") String category) {
        List<Product> allProducts = productService.getAllActiveProducts(category);
        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        model.addAttribute("products", allProducts);
        model.addAttribute("categories", allActiveCategory);
        model.addAttribute("paramValue",category);
        return "product";
    }

    @GetMapping("/product/{id}")
    public String product(Model model, @PathVariable int id) {
        Product productById = productService.getProductById(id);
        model.addAttribute("product", productById);
        return "view_product";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, @RequestParam("file") MultipartFile file, HttpSession session)
            throws IOException {

        UserDtls saveUser = userService.saveUser(user,file);

        if (!ObjectUtils.isEmpty(saveUser)) {
            return "successRegister";
        }

        return "errorRegister";
    }

    @ModelAttribute
    public void getUserDetails(Principal principal,Model model){
        if(principal != null){
            String email = principal.getName();
            UserDtls userByEmail = userService.getUserByEmail(email);
            model.addAttribute("user", userByEmail);
            Integer cartCountForUser = cartService.getCartCountForUser(userByEmail.getId());
            model.addAttribute("countCart", cartCountForUser);
        }
        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        model.addAttribute("categories", allActiveCategory);
    }
}
