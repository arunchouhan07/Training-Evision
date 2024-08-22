package com.ecom.service;

import com.ecom.model.Cart;

import java.util.List;

public interface CartService {
    //below methods are implicitly public
    Cart saveInCartForUser(Integer productId, Integer userId);

    List<Cart> getAllCartForUser(Integer userId);
}
