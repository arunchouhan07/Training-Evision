package com.ecom.service;

import com.ecom.entity.ProductOrder;
import com.ecom.model.OrderRequest;

import java.util.List;

public interface ProductOrderService {

    List<ProductOrder> save(Integer userId, OrderRequest orderRequest);

    ProductOrder getProductOrder(Integer productId);

    List<ProductOrder> getALlProductOrder(Integer id, String orderStatus);

    ProductOrder updateProductOrder(ProductOrder productOrder);

    void deleteProductOrder(String productId);
}
