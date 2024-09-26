package com.ecom.entity;

public enum OrderStatus {
    ORDER_CONFIRMED(1, "Order Confirmed"),
    ORDER_IN_PROGRESS(2, "Order InProgress"),
    ORDER_SHIPPED(3, "Order Shipped"),
    ORDER_CANCELED(4, "Order Canceled"),
    ORDER_OUT_FOR_DELIVERY(5, "Order Out For Delivery"),
    ORDER_DELIVERED(6, "Order Delivered");

    private final int id;
    private final String description;

    OrderStatus(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
