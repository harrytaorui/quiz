package com.twuc.shopping.api;

import com.twuc.shopping.Service.OrderService;
import com.twuc.shopping.dto.Order;
import com.twuc.shopping.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/orders/{productId}")
    public ResponseEntity deleteOrder(@PathVariable int productId) {
        boolean deleted = orderService.deleteOrder(productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.badRequest().body("该订单不存在");
        }
    }
}
