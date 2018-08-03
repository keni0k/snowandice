package com.example.user;

import java.util.List;
import java.util.Optional;

/**
 * Created by Keni0k on 25.07.2018.
 */

public interface UserService {
    User addUser(User User);

    void delete(long id);

    User getByToken(String token);

    User getByEmail(String email);

    Optional<User> getById(long id);

    User editUser(User User);

    Boolean isLoginFree(String login);

    Boolean isEmailFree(String login);

    Boolean isEmailCorrect(String login);

    Boolean throwsErrors(User User, String pass2);

    Boolean isPhoneFree(String login);

    Boolean authorization(String login, String pass);

    Iterable<User> getAll();

    List<User> getByFilter(Integer type, Long rateDown, Long rateUp, String firstName, String lastName, String city, Integer sortBy);

}
