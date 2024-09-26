package com.ecom.repository;

import com.ecom.entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c where c.userDtls.id= :userDtlsId and c.product.id= :productDtlsId and c.isActive= true")
    Cart findByUserDtlsIdAndProductId(Integer userDtlsId, Integer productDtlsId);

    @Query("select c from Cart c where c.userDtls.id=:userDtlsId and c.quantity > 0 and c.isActive= true")
    List<Cart> findAllProductByUserDtlsId(Integer userDtlsId);

    @Query("select count(c) from Cart c where c.isActive= true")
    Integer countProductByUserDtlsId(Integer userDtlsId);

    @Transactional
    @Modifying
    @Query("update Cart c set c.isActive= false where c.userDtls.id= :userId")
    void deleteCartItemsForUser(Integer userId);
}
