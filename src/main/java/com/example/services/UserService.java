package com.example.services;

import com.example.models.User;

import java.util.List;

/**
 * Created by Keni0k on 25.07.2018.
 */

public interface UserService extends BaseService<User> {

    Boolean isEmailFree(String login);

    Boolean isEmailCorrect(String login);

    Boolean throwsErrors(User User, String pass2);

    Boolean isPhoneFree(String login);

    List<User> getByFilter(Integer type, String firstName, String lastName, Integer sortBy);

}