package com.twuc.shopping.api;

import com.twuc.shopping.Entity.OrderEntity;
import com.twuc.shopping.Service.OrderService;
import com.twuc.shopping.dto.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrder() {
        List<Order> orderList = orderService.getOrders();
        return ResponseEntity.ok().body(orderList);
    }

    @PostMapping("/orders")
    public ResponseEntity addOrder(@RequestBody Order order) {
        OrderEntity orderEntity = orderService.createOrder(order.getProducts());
        if (orderEntity == null) {
            return ResponseEntity.badRequest().body("商品不存在");
        }
        return ResponseEntity.status(201).header("index", String.valueOf(orderEntity.getId())).build();
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable int orderId) {
        boolean deleted = orderService.deleteOrder(orderId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.badRequest().body("该订单不存在");
        }
    }
}
