package com.learning.springdemo.data;

import com.learning.springdemo.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Order save(Order order);
}
