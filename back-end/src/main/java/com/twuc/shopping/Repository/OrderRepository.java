package com.twuc.shopping.Repository;

import com.twuc.shopping.Entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findAll();
}
