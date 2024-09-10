package com.ecom.service;

import com.ecom.entity.Cart;

import java.util.List;

public interface CartService {
    //below methods are implicitly public
    Cart saveInCartForUser(Integer productId, Integer userId);

    List<Cart> getAllCartForUser(Integer userId);

    Double getOverAllPrice();

    Integer getCartCountForUser(Integer userId);

    Cart updateCartQuantityForUser(String sy, Integer cid);
}
