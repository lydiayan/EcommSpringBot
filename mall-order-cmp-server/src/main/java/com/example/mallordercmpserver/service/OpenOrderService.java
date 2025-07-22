package com.example.mallordercmpserver.service;
import com.example.mallordercmpserver.data.Order;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author: jyy
 * @Desc:
 **/
@Service
public class OpenOrderService {

    private static final String BASE_URL = "http://localhost:8081/orders";

    private final RestTemplate restTemplate;

    public OpenOrderService() {
        this.restTemplate = new RestTemplate();
    }

    @Tool(description = "获取所有订单信息" )
    public List<Order> getOrders() {
        // 尝试远程调用
        System.out.println("获取所有订单信息");
        String url = BASE_URL+"/list";
        return restTemplate.getForObject(url, List.class);
    }

    @Tool(description = "根据用户ID获取用户订单列表信息" )
    public List<Order> getOrdersByUserId(String userId) {
        System.out.println("根据用户ID获取用户订单列表信息");
        // 尝试远程调用
        String url = BASE_URL+"/user/" + userId;
        return restTemplate.getForObject(url, List.class);

    }
    @Tool(description = "根据订单ID获取订单详情" )
    public Order getOrderById(String orderId) {
        System.out.println("根据订单ID获取订单详情");
        String url = BASE_URL+"/{orderId}";
        return restTemplate.getForObject(url, Order.class, orderId);
    }

    @Tool(description = "根据订单ID取消订单" )
    public boolean cancelOrder(String orderId) {
        System.out.println("根据订单ID取消订单");
        String url = BASE_URL+"/cancel/{orderId}";
        return restTemplate.postForObject(url, null, Boolean.class, orderId);
    }
}
