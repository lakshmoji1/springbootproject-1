package com.example.order.client;

import com.example.order.dto.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "customer-client")
public interface CustomerClient {
    @GetMapping("/getCustomerDetails")
    public Customer getCustomerDetails();
}
