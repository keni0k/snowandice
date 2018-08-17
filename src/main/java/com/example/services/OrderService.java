package com.example.services;

import com.example.models.Order;

import java.util.List;

public interface OrderService extends BaseService<Order> {

    List<Order> getByIdOfUser(long id);

    List<Order> getByType (int type);

    List<Order> getByFilter();

}
