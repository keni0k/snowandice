package com.example.jpa_services_impl;

import com.example.models.Log;
import com.example.models.User;
import com.example.repo.LogRepository;
import com.example.services.LogService;

import java.util.List;

public class LogServiceImpl implements LogService {

    private LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void add(Log log) {
        logRepository.saveAndFlush(log);
    }
    @Override
    public void update(Log log) {
        logRepository.saveAndFlush(log);
    }
    @Override
    public void delete(Log log) {
        logRepository.delete(log);
    }
    @Override
    public void delete(Long id) {
        logRepository.deleteById(id);
    }
    @Override
    public Log findById(Long id) {
        return logRepository.getOne(id);
    }
    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }
    @Override
    public List<Log> getAllByLevel(int level){
        return logRepository.getAllByLevel(level);
    }
    @Override
    public List<Log> getAllByUser(User user){
        return logRepository.getAllByUser(user);
    }

}
