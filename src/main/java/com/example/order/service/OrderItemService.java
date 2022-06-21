package com.example.order.service;

import com.example.order.entity.CustomerOrder;
import com.example.order.entity.OrderItem;
import com.example.order.entity.OrderStatus;
import com.example.order.repository.CustomerOrderRepository;
import com.example.order.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private OrderItemRepository itemRepository;

    public void createOrderItem(OrderItem item) {
        System.out.println(item.getOrderId());
        Optional<CustomerOrder> order = orderRepository.findById(item.getOrderId());
        if(order == null)
            System.out.println("Order doesn't exists");
        else if(order.get().getStatus().equals(OrderStatus.NotPlaced)) {
            order.get().addItem(item);
            orderRepository.save(order.get());
        }
        else
            System.out.println("OrderItem cannot be added");
    }

}
