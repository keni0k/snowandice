package com.example.repo;

import com.example.models.callbacks.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusCallbackRepository extends JpaRepository<Status, Long> {

}