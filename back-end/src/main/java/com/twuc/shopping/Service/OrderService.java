package com.twuc.shopping.Service;

import com.twuc.shopping.Entity.OrderEntity;
import com.twuc.shopping.Repository.OrderRepository;
import com.twuc.shopping.dto.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    final OrderRepository orderRepository;

    final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public List<Order> getOrders() {
        List<OrderEntity> orderList = orderRepository.findAll();
        return orderList.stream().map(e -> Order.builder()
                .product(e.getProduct())
                .amount(e.getAmount())
                .build()).collect(Collectors.toList());
    }

    public boolean deleteOrder(Integer id) {
        Optional<OrderEntity> result = orderRepository.findById(id);
        if (!result.isPresent()) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }
}
