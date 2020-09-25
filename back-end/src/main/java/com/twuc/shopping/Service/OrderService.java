package com.twuc.shopping.Service;

import com.twuc.shopping.Entity.OrderEntity;
import com.twuc.shopping.Entity.ProductEntity;
import com.twuc.shopping.Repository.OrderRepository;
import com.twuc.shopping.Repository.ProductRepository;
import com.twuc.shopping.dto.Order;
import com.twuc.shopping.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    final OrderRepository orderRepository;

    final ProductService productService;

    final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductService productService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    public List<Order> getOrders() {
        List<OrderEntity> orderList = orderRepository.findAll();
        return orderList.stream().map(e -> Order.builder()
                .product(productService.convertEntityToProduct(e.getProductEntity()))
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

    public OrderEntity createOrder(Order order) {

        Optional<ProductEntity> result = productRepository.findByName(order.getProduct().getName());
        if (!result.isPresent()) {
            return null;
        }
        ProductEntity productEntity = result.get();
        OrderEntity orderEntity = OrderEntity.builder()
                .amount(order.getAmount())
                .productEntity(productEntity)
                .build();
        orderRepository.save(orderEntity);
        return orderEntity;
    }
}
