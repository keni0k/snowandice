package com.example.repo;

import com.example.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getOrderById (long id);

    List<Order> getOrdersByIdOfUser(long id);

    List<Order> getOrdersByType (int type);
}
