package com.ecom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private UserDtls userDtls;

    @ManyToOne
    private Product product;

    private Integer quantity;

    private Double totalPrice;

    private Double allOverPrice;

    private Boolean isActive;

}
