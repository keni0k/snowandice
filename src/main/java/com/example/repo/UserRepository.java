package com.example.repo;

import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByEmail(String email);

    User getUserByPhoneNumber(String phoneNumber);
}
