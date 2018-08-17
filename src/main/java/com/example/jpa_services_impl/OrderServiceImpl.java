package com.example.jpa_services_impl;

import com.example.cart.CartLineInfo;
import com.example.models.Order;
import com.example.repo.CartLineInfoRepository;
import com.example.repo.OrderRepository;
import com.example.services.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private CartLineInfoRepository cartLineInfoRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartLineInfoRepository cartLineInfoRepository) {
        this.orderRepository = orderRepository;
        this.cartLineInfoRepository = cartLineInfoRepository;
    }

    public void addCartLine(CartLineInfo cartLineInfo){
        cartLineInfoRepository.saveAndFlush(cartLineInfo);
    }

    public List<CartLineInfo> getCartLinesByOrderId(long orderId){
        return cartLineInfoRepository.getCartLineInfosByOrderId(orderId);
    }

    public List<CartLineInfo> getAllCartLines(){
        return cartLineInfoRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.getOrderById(id);
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
    public void add(Order order) {
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void update(Order order) {
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getByFilter() {
        return orderRepository.findAll();
    }
}
