package com.mall.controller;

import com.mall.domains.dto.OrderDTO;
import com.mall.domains.po.Order;
import com.mall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrder/{id}")
    public Order getOrder(@PathVariable("id") Integer orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping("/createOrder")
    public Integer createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PostMapping("/confirmRecipt/{id}")
    public void confirmRecipt(@PathVariable("id") Integer orderId) {
        orderService.confirmRecipt(orderId);
    }

    @PostMapping("/cancelOrder/{id}")
    public void cancelOrder(@PathVariable("id") Integer orderId) {
        orderService.cancelOrder(orderId);
    }

}
