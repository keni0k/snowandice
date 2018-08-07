package com.example.order;

import com.example.repo.OrderRepository;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Order getById(long id) {
        return orderRepository.getOrderById(id);
    }

    @Override
    public List<Order> getByIdOfProduct(long id) {
        return orderRepository.getOrdersByIdOfProduct(id);
    }

    @Override
    public List<Order> getByIdOfUser(long id) {
        return orderRepository.getOrdersByIdOfUser(id);
    }

    @Override
    public List<Order> getByType(int type) {
        return orderRepository.getOrdersByType(type);
    }

    @Override
    public void addOrder(Order order) {
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void editOrder(Order order) {
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getByFilter() {
        return orderRepository.findAll();
    }
}
