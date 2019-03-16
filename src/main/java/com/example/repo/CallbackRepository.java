package com.example.repo;

import com.example.models.callbacks.Callback;
import com.example.models.callbacks.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CallbackRepository extends JpaRepository<Callback, Long> {

    Callback getCallbackById (long id);

    List<Callback> getCallbacksByPhone(String phone);

    List<Callback> getCallbacksByStatus(Status status);

}