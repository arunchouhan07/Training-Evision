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
             cart.setTotalPrice(product.getDiscountPrice());
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
        List<Cart> allProductByUserDtlsId = cartRepository.findAllProductByUserDtlsId(userId);
        allProductByUserDtlsId.stream().forEach(cart->cart.setTotalPrice(cart.getProduct().getDiscountPrice()*cart.getQuantity()));
        return allProductByUserDtlsId;
    }

    @Override
    public Integer getCartCountForUser(Integer userId) {
        return cartRepository.countProductByUserDtlsId(userId);
    }

    @Override
    public Cart updateCartQuantityForUser(String sy, Integer cid) {
        Cart cart = cartRepository.findById(cid).get();

        if(sy.equals("de")) {
            if (cart.getQuantity() <= 1) {
                cartRepository.delete(cart);
            }
            else{
                cart.setQuantity(cart.getQuantity() - 1);
                cart.setTotalPrice(cart.getTotalPrice()-cart.getProduct().getDiscountPrice());
                cartRepository.save(cart);
            }
        }else if(sy.equals("in")){
            cart.setQuantity(cart.getQuantity()+1);
            cart.setTotalPrice(cart.getTotalPrice()+cart.getProduct().getDiscountPrice());
            cartRepository.save(cart);
        }

        return cart;
    }
}
