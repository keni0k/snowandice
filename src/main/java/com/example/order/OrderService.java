package com.example.order;

import com.example.repo.RepoService;

import java.util.List;

public interface OrderService extends RepoService<Order> {

    List<Order> getByIdOfUser(long id);

    List<Order> getByType (int type);

    List<Order> getByFilter();

}
