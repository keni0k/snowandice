package com.example.order;

import java.util.List;

public interface OrderService {

    Order getById (long id);

    List<Order> getByIdOfProduct (long id);

    List<Order> getByIdOfUser(long id);

    List<Order> getByType (int type);

    void addOrder(Order order);
    void editOrder(Order order);

    void deleteOrder(Order order);
    void deleteOrder(long id);

    List<Order> getAll();
    List<Order> getByFilter();

}
