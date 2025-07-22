package com.example.mallorder.controller;

import com.example.mallorder.entity.Order;
import com.example.mallorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 订单列表查询
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/list")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    // 订单详情查询（根据订单号）
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    // 订单列表查询（根据用户ID）
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable String userId) {
        return orderService.getOrdersByUserId(userId);
    }

    // 取消订单（根据订单号）
    @PostMapping("/cancel/{orderId}")
    public boolean cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(orderId);
    }
}