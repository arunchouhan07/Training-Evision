package com.ecom.controller;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/singing")
    public String login() {
        logger.info("singing method called");
        return "login";
    }

    @GetMapping("/login")
    public String login1() {
        logger.info("singing method called");
        return "login";
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
        }
        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        model.addAttribute("categories", allActiveCategory);
    }

}
