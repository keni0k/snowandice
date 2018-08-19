package com.example.repo;

import com.example.models.User;
import com.example.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getOrderById (long id);

    List<Order> getOrdersByUser(User user);

    List<Order> getOrdersByType (int type);
}
