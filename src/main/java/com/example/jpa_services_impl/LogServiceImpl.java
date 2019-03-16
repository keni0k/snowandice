package com.example.jpa_services_impl;

import com.example.models.Log;
import com.example.models.User;
import com.example.repo.LogRepository;

import java.util.List;

public class LogServiceImpl {

    private LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void add(Log log) {
        logRepository.saveAndFlush(log);
    }
    public void update(Log log) {
        logRepository.saveAndFlush(log);
    }

    public void delete(Log log) {
        logRepository.delete(log);
    }
    public void delete(Long id) {
        logRepository.deleteById(id);
    }

    public Log findById(Long id) {
        return logRepository.getOne(id);
    }
    public List<Log> findAll() {
        return logRepository.findAll();
    }

    public List<Log> getAllByLevel(int level){
        return logRepository.getAllByLevel(level);
    }
    public List<Log> getAllByUser(User user){
        return logRepository.getAllByUser(user);
    }

}
