package com.ecom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderId;

    private Date orderDate;

    @ManyToOne
    private Product product;

    @ManyToOne
    private UserDtls userDtls;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderBillingDetails orderBillingDetails;

    private Double price;

    private Integer quantity;

    private String orderStatus;

    private String paymentMethod;
}
