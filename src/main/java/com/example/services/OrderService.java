package com.example.services;

import com.example.models.User;
import com.example.models.order.Order;

import java.util.List;

public interface OrderService extends BaseService<Order> {

    List<Order> getByUser(User user);

    List<Order> getByType (int type);

    List<Order> getByFilter();

}
