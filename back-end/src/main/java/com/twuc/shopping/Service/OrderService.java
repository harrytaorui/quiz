package com.twuc.shopping.Service;

import com.twuc.shopping.Entity.OrderDetailEntity;
import com.twuc.shopping.Entity.OrderEntity;
import com.twuc.shopping.Entity.ProductEntity;
import com.twuc.shopping.Repository.OrderDetailRepository;
import com.twuc.shopping.Repository.OrderRepository;
import com.twuc.shopping.Repository.ProductRepository;
import com.twuc.shopping.dto.Order;
import com.twuc.shopping.dto.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    final OrderRepository orderRepository;

    final ProductRepository productRepository;

    final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> getOrders() {
        List<OrderEntity> orderList = orderRepository.findAll();
        return orderList.stream().map(orderEntity ->
                Order.builder().products(getProducts(orderEntity.getOrderDetailEntities())).id(orderEntity.getId())
                        .build())
                .collect(Collectors.toList());
    }

    public boolean deleteOrder(Integer id) {
        Optional<OrderEntity> result = orderRepository.findById(id);
        if (!result.isPresent()) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    public OrderEntity createOrder(List<Product> productList) {
        List<OrderDetailEntity> orderDetailEntities = productList.stream().map(product ->
                convertProductToEntity(product)).collect(Collectors.toList());
        OrderEntity orderEntity = OrderEntity.builder()
                .OrderDetailEntities(orderDetailEntities)
                .build();
        orderRepository.save(orderEntity);
        return orderEntity;
    }

    private OrderDetailEntity convertProductToEntity(Product product) {
        OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder()
                .num(product.getNum())
                .productId(product.getId())
                .build();
        return orderDetailEntity;
    }

    private List<Product> getProducts(List<OrderDetailEntity> orderDetailEntities) {
        List<ProductEntity> products = orderDetailEntities.stream().map(detail ->
                productRepository.findById(detail.getProductId()).get()).collect(Collectors.toList());
        List<Integer> nums = orderDetailEntities.stream().map(OrderDetailEntity::getNum).collect(Collectors.toList());
        List<Product> result = products.stream().map(p -> Product.builder()
                .price(p.getPrice())
                .unit(p.getUnit())
                .imgUrl(p.getImgUrl())
                .name(p.getName())
                .id(p.getId())
                .build()).collect(Collectors.toList());
        for (int i=0;i<result.size();i++) {
            result.get(i).setNum(nums.get(i));
        }
        return result;
    }
}
