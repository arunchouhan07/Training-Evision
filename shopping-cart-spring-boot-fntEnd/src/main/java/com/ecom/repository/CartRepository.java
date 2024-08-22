package com.ecom.repository;

import com.ecom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserDtlsIdAndProductId(Integer userDtlsId, Integer productDtlsId);

    List<Cart> findAllProductByUserDtlsId(Integer userDtlsId);
}
