package com.ecom.repository;

import com.ecom.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

}
