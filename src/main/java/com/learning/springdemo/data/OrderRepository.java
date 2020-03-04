package com.learning.springdemo.data;

import com.learning.springdemo.Order;

public interface OrderRepository {
    Order save(Order order);
}
