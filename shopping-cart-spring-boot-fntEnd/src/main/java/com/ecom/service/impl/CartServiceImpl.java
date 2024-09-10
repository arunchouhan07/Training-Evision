package com.ecom.service.impl;

import com.ecom.entity.Cart;
import com.ecom.entity.Product;
import com.ecom.entity.UserDtls;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    List<Cart> allProductByUserDtlsId = new ArrayList<Cart>();

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
             cart.setTotalPrice(product.getDiscountPrice());
        }else{
            cart=getCartDetails;
            cart.setQuantity(cart.getQuantity()+1);
            cart.setTotalPrice(cart.getQuantity()*product.getDiscountPrice());
        }
        cart.setAllOverPrice(cart.getTotalPrice());
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getAllCartForUser(Integer userId) {
        allProductByUserDtlsId = cartRepository.findAllProductByUserDtlsId(userId);

        return allProductByUserDtlsId;
    }

    @Override
    public Double getOverAllPrice(){
        double allAddedPrice = 0.0;
        allAddedPrice = allProductByUserDtlsId.stream()
                .map(Cart::getTotalPrice).reduce(0.0, Double::sum);
        double finalAllAddedPrice = allAddedPrice;
        allProductByUserDtlsId.stream().forEach(cart->cart.setAllOverPrice(finalAllAddedPrice));
        return allAddedPrice;
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
