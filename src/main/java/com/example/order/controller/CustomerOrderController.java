package com.example.order.controller;

import com.example.order.entity.CustomerOrder;
import com.example.order.entity.OrderItem;
import com.example.order.entity.OrderStatus;
import com.example.order.repository.CustomerOrderRepository;
import com.example.order.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/order")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderRepository repo;

    @Autowired
    private OrderItemRepository itemRepository;

    @GetMapping("/get/{id}")
    public CustomerOrder getOrderById(@PathVariable("id") Integer id) {
        return repo.findById(id).get();
    }

    @GetMapping("/user/{{id}}/history")
    public List<CustomerOrder> getAllPlacedOrders(@PathVariable("id") Integer id) {
        return repo.findByUserIdAndStatus(id, OrderStatus.Placed);
    }

    @GetMapping("/user/{{id}}/current")
    public CustomerOrder getCurrentOrder(@PathVariable("id") Integer id) {
        List<CustomerOrder> currentOrder = repo.findByUserIdAndStatus(id, OrderStatus.NotPlaced);
        if(currentOrder.size() == 1)
            return currentOrder.get(0);
        else
            return null;
    }

    @PostMapping("/create/user/{{userId}}/medicine/{{medicineId}}/batch/{{batchId}}")
    public CustomerOrder createNewOrder(@PathVariable("userId") Integer userId, @PathVariable("medicineId") Integer medicineId, @PathVariable("batchId") Integer batchId) {
        List<CustomerOrder> unPlacedOrder = repo.findByUserIdAndStatus(userId, OrderStatus.NotPlaced);
        if(unPlacedOrder.size() == 0) {
            CustomerOrder order = new CustomerOrder();
            order.setUserId(userId);
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());
            order.setStatus(OrderStatus.NotPlaced);

            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setQuantity(1);
            item.setMedicineId(medicineId);
            item.setBatchId(batchId);

            order.setOrderItems(Arrays.asList(item));

            return repo.save(order);
        }
        else
            return unPlacedOrder.get(0);
    }

    @GetMapping("/save")
    public String saveOrder() {

        CustomerOrder order1 = new CustomerOrder();
        order1.setUserId(1);
        order1.setCreatedAt(LocalDateTime.now());
        order1.setUpdatedAt(LocalDateTime.now());
        order1.setStatus(OrderStatus.NotPlaced);

        OrderItem item1 = new OrderItem();
        item1.setOrderId(1);
        item1.setQuantity(1);
        item1.setMedicineId(101);
        item1.setBatchId(201);

        OrderItem item2 = new OrderItem();
        item2.setOrderId(1);
        item2.setQuantity(2);
        item2.setMedicineId(102);
        item2.setBatchId(202);

        order1.setOrderItems(Arrays.asList(item1, item2));

        repo.save(order1);

        CustomerOrder order2 = new CustomerOrder();
        order2.setUserId(2);
        order2.setCreatedAt(LocalDateTime.now().plus(2, ChronoUnit.HOURS));
        order2.setUpdatedAt(LocalDateTime.now().plus(2, ChronoUnit.HOURS));
        order2.setStatus(OrderStatus.Placed);
        repo.save(order2);

        return "two orders are placed";
    }

    @GetMapping("/user/{{userId}}/confirm")
    public CustomerOrder saveOrder(@PathVariable("userId") Integer userId) {
        List<CustomerOrder> unPlacedOrder = repo.findByUserIdAndStatus(userId, OrderStatus.NotPlaced);
        if(unPlacedOrder.size() == 1) {
            unPlacedOrder.get(0).setStatus(OrderStatus.Placed);
            return repo.save(unPlacedOrder.get(0));
        }
        else return null;
    }

    @PostMapping("/{{orderId}}/add/orderItem/medicine/{{medicineId}}/batch/{{batchId}}")
    public CustomerOrder addNewOrderItem(@PathVariable("orderId") Integer orderId, @PathVariable("medicineId") Integer medicineId, @PathVariable("batchId") Integer batchId ) {
        OrderItem newItem = new OrderItem();
        newItem.setOrderId(orderId);
        newItem.setQuantity(1);
        newItem.setBatchId(batchId);
        newItem.setMedicineId(medicineId);

        CustomerOrder order = repo.findById(orderId).get();
        order.getOrderItems().add(newItem);

        return repo.save(order);
    }

}
