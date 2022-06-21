package com.example.order.controller;

import com.example.order.entity.OrderItem;
import com.example.order.service.OrderItemService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemController {

    @Autowired
    private OrderItemService itemService;



    @GetMapping("/add")
    public String createOrderItem() {
        OrderItem item = new OrderItem();
        item.setQuantity(1);
        item.setOrderId(1);
        itemService.createOrderItem(item);

        return "Order Item is added";
    }
}
