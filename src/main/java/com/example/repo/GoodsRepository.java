package com.example.repo;

import com.example.models.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Good, Long> {

    Good getCallbackById (long id);
}
