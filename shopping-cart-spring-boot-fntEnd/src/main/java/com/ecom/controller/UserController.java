package com.ecom.controller;

import com.ecom.model.Cart;
import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

     @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String index() {
        return "user/home";
    }

    @GetMapping("/addCart")
    public String addCart(@RequestParam int pid, @RequestParam int uid, Model model) {
        Cart cart = cartService.saveInCartForUser(pid, uid);
        //model.addAttribute("carts", cart);
        return "redirect:/user/cart?uid="+uid;
    }

    @GetMapping("/cart")
    public String getAllCartItems(@RequestParam int uid, Model model) {
        List<Cart> allCartForUser = cartService.getAllCartForUser(uid);
        model.addAttribute("carts", allCartForUser);
        return "user/cart";
    }


    @ModelAttribute
    public void getUserDetails(Principal principal, Model model){
        if(principal != null){
            String email = principal.getName();
            UserDtls userByEmail = userService.getUserByEmail(email);
            model.addAttribute("user", userByEmail);
        }
        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        model.addAttribute("categories", allActiveCategory);
    }
}
