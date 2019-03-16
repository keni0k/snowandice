package com.example.repo;

import com.example.models.Log;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> getAllByLevel(int level);
    List<Log> getAllByUser(User user);

}
