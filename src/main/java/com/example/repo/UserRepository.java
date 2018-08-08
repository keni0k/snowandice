package com.example.repo;

import com.example.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByEmail(String email);

    User getUserByLoginOrEmail(String login, String email);

    User getUserByLogin(String login);

    User getUserByPhoneNumber(String phoneNumber);
}
