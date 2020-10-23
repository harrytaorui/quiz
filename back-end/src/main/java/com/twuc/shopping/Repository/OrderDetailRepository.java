package com.twuc.shopping.Repository;

import com.twuc.shopping.Entity.OrderDetailEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailRepository extends CrudRepository<OrderDetailEntity, Integer> {
}
