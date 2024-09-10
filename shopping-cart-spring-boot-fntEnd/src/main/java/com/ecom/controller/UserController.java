package com.ecom.controller;

import com.ecom.entity.Cart;
import com.ecom.entity.Category;
import com.ecom.entity.ProductOrder;
import com.ecom.entity.UserDtls;
import com.ecom.model.OrderRequest;
import com.ecom.repository.CartRepository;
import com.ecom.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private ProductOrderService productOrderService;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("")
    public String index() {
        return "user/home";
    }

    @GetMapping("/addCart")
    public String addCart(@RequestParam int pid, @RequestParam int uid, Model model) {
        Cart cart = cartService.saveInCartForUser(pid, uid);
        model.addAttribute("carts", cart);
        return "redirect:/user/cart?uid="+uid;
    }

    @GetMapping("/cart")
    public String getAllCartItems(@RequestParam int uid, Model model) {
        List<Cart> allCartForUser = cartService.getAllCartForUser(uid);
        Double allOverPrice = cartService.getOverAllPrice();
        model.addAttribute("carts", allCartForUser);
        model.addAttribute("allOverPrice", allOverPrice);
        return "user/cart";
    }

    @GetMapping("/cartQuantityUpdate")
    public String updateCartQuantity(@RequestParam String sy, @RequestParam int cid){
        Cart cart = cartService.updateCartQuantityForUser(sy, cid);
        int uid=cart.getUserDtls().getId();
        return "redirect:/user/cart?uid="+uid;
    }

    @GetMapping("/orders")
    public String orders(Model model, Principal principal) {
        UserDtls loggedInUserDetails = getLoggedInUserDetails(principal);
        Double overAllPrice = cartService.getOverAllPrice();
        model.addAttribute("overAllPrice", overAllPrice);
        model.addAttribute("user", loggedInUserDetails);
        return "user/order";
    }

    @PostMapping("/save-order")
    public String saveOrder(@ModelAttribute OrderRequest orderRequest, Principal principal, Model model) {
        UserDtls loggedInUserDetails = getLoggedInUserDetails(principal);
        List<ProductOrder> savedProductOrder = productOrderService.save(loggedInUserDetails.getId(), orderRequest);
        model.addAttribute("savedProductOrder", savedProductOrder.get(0));
        model.addAttribute("orderRequest", orderRequest);
        return (!ObjectUtils.isEmpty(savedProductOrder))? "user/orderSuccess" : "user/orderError";
    }

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model){
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

    public UserDtls getLoggedInUserDetails(Principal principal) {
        String email = principal.getName();
        return userService.getUserByEmail(email);
    }
}
