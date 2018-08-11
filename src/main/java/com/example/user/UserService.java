package com.example.user;

import java.util.List;

/**
 * Created by Keni0k on 25.07.2018.
 */

public interface UserService {
    void addUser(User User);

    void delete(long id);

    User getById(long id);

    void editUser(User User);

    Boolean isLoginFree(String login);

    Boolean isEmailFree(String login);

    Boolean isEmailCorrect(String login);

    Boolean throwsErrors(User User, String pass2);

    Boolean isPhoneFree(String login);

    List<User> getAll();

    List<User> getByFilter(Integer type, String firstName, String lastName, String city, Integer sortBy);

}