package com.ecom.repository;

import com.ecom.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
    @Query("select p from ProductOrder p where p.userDtls.id= :id and p.orderStatus= :orderStatus")
    List<ProductOrder> getAllProductOrderSuccess(Integer id, String orderStatus);
}
