package com.example.spring_core1.order;

public interface OrderService {

    Order createOrder(Long memberId, String itemName, int itemPrice);
}
