package com.ecom.service.impl;

import com.ecom.entity.Cart;
import com.ecom.entity.OrderBillingDetails;
import com.ecom.entity.OrderStatus;
import com.ecom.entity.ProductOrder;
import com.ecom.model.OrderRequest;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductOrderRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductOrder> save(Integer userId, OrderRequest orderRequest){
        List<ProductOrder> listProductOrder = new ArrayList<>();
        try{
            List<Cart> allProductByUserDtlsId = cartRepository.findAllProductByUserDtlsId(userId);
            if(allProductByUserDtlsId.isEmpty()){
                throw new RuntimeException("Please Add Item to the Cart");
            }
            for (Cart cart : allProductByUserDtlsId) {
                ProductOrder productOrder = new ProductOrder();
                productOrder.setOrderId(UUID.randomUUID().toString().substring(5,28));
                productOrder.setOrderDate(new Date());
                productOrder.setProduct(cart.getProduct());
                productOrder.setUserDtls(cart.getUserDtls());
                productOrder.setPrice(cart.getProduct().getDiscountPrice());
                productOrder.setQuantity(cart.getQuantity());
                productOrder.setOrderStatus(OrderStatus.ORDER_CONFIRMED.getDescription());
                productOrder.setPaymentMethod(orderRequest.getPaymentMethod());

                OrderBillingDetails orderBillingDetails = new OrderBillingDetails();

                orderBillingDetails.setFirstName(orderRequest.getFirstName());
                orderBillingDetails.setLastName(orderRequest.getLastName());
                orderBillingDetails.setEmail(orderRequest.getEmail());
                orderBillingDetails.setPhone(orderRequest.getPhone());
                orderBillingDetails.setAddress(orderRequest.getAddress());
                orderBillingDetails.setCity(orderRequest.getCity());
                orderBillingDetails.setState(orderRequest.getState());
                orderBillingDetails.setPinCode(orderRequest.getPinCode());

                productOrder.setOrderBillingDetails(orderBillingDetails);
                productOrder = productOrderRepository.save(productOrder);
                listProductOrder.add(productOrder);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return listProductOrder;
    }

    @Override
    public ProductOrder getProductOrder(Integer productId) {
        return productOrderRepository.findById(productId).get();
    }

    @Override
    public List<ProductOrder> getALlProductOrder(Integer id, String orderStatus) {
        return productOrderRepository.getAllProductOrderSuccess(id, orderStatus);
    }

    @Override
    public ProductOrder updateProductOrder(ProductOrder productOrder) {
        return null;
    }

    @Override
    public void deleteProductOrder(String productId) {

    }
}
