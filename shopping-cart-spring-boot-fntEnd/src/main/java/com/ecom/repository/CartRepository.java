package com.ecom.repository;

import com.ecom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserDtlsIdAndProductId(Integer userDtlsId, Integer productDtlsId);

    @Query("select c from Cart c where c.userDtls.id=:userDtlsId and c.quantity > 0 ")
    List<Cart> findAllProductByUserDtlsId(Integer userDtlsId);

    Integer countProductByUserDtlsId(Integer userDtlsId);
}
