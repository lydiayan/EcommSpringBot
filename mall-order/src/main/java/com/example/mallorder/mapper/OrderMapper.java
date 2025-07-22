package com.example.mallorder.mapper;

import com.example.mallorder.entity.Order;
import com.example.mallorder.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM orders limit 10")
    List<Order> selectAllOrders();

    @Select("SELECT * FROM orders WHERE order_id = #{orderId}")
    Order selectOrderById(@Param("orderId") String orderId);

    @Select("SELECT * FROM order_details WHERE order_id = #{orderId}")
    List<OrderDetail> selectOrderDetailsByOrderId(@Param("orderId") String orderId);

    @Select("SELECT * FROM orders WHERE user_id = #{userId} ORDER BY order_time DESC")
    List<Order> selectOrdersByUserId(@Param("userId") String userId);

    @Update("UPDATE orders SET order_status = 4 WHERE order_id = #{orderId}")
    int cancelOrder(@Param("orderId") String orderId);
}