package com.example.jalyaan;

import java.time.LocalDateTime;

public class Order {
    String orderID;
    String uid;
    String quantiy;
    String price;
    boolean isDelivered;
    LocalDateTime date;
    public Order(String orderID, String uid, String quantiy, String price) {
        this.orderID = orderID;
        this.uid = uid;
        this.quantiy = quantiy;
        this.price = price;
        this.isDelivered = false;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getUid() {
        return uid;
    }

    public String getQuantiy() {
        return quantiy;
    }

    public String getPrice() {
        return price;
    }
}
