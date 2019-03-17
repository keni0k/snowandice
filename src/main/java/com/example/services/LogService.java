package com.example.services;

import com.example.models.Log;
import com.example.models.User;

import java.util.List;

public interface LogService  extends BaseService<Log>{

    List<Log> getAllByLevel(int level);

    List<Log> getAllByUser(User user);

}