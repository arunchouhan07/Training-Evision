package com.ecom.service.impl;

import com.ecom.model.Cart;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart saveInCartForUser(Integer productId, Integer userId) {
        Cart getCartDetails = cartRepository.findByUserDtlsIdAndProductId(userId, productId);
        UserDtls userDtls = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();
        Cart cart=null;

        if (ObjectUtils.isEmpty(getCartDetails)) {
             cart=new Cart();
             cart.setUserDtls(userDtls);
             cart.setProduct(product);
             cart.setQuantity(1);
             cart.setProductPrice(product.getPrice());
             cart.setTotalPrice(product.getPrice());
        }else{
            cart=getCartDetails;
            cart.setQuantity(cart.getQuantity()+1);
            cart.setProductPrice(product.getPrice());
            cart.setTotalPrice(cart.getQuantity()*product.getPrice());
        }
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getAllCartForUser(Integer userId) {
        return cartRepository.findAllProductByUserDtlsId(userId);
    }
}
