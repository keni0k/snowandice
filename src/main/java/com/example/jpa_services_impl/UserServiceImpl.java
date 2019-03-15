package com.example.jpa_services_impl;

import com.example.models.User;
import com.example.repo.UserRepository;
import com.example.services.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keni0k on 25.07.2017.
 */

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void add(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByEmail(String email) {
        email = email.toLowerCase();
        return userRepository.getUserByEmail(email);
    }

    @Override
    public List<User> getByFilter(Integer type, String firstName, String lastName, Integer sortBy) {

        if (type==null) type = -1;
        if (firstName==null) firstName = "";
        if (lastName==null) lastName = "";
        List<User> list = findAll();

        List<User> copy = new ArrayList<>();
        for (User aList : list) {
            boolean isEqualsType = aList.getType() == type || type == -1;
            boolean isContainsFirstName = aList.getFirstName().toLowerCase().contains(firstName.toLowerCase());
            boolean isContainsLastName = aList.getLastName().toLowerCase().contains(lastName.toLowerCase());
            if (isEqualsType && isContainsFirstName && isContainsLastName)
                copy.add(aList);
        }
        if(sortBy!=null)
        copy.sort((o1, o2) -> {
            switch (sortBy) {
                case 0:
                    return Long.compare(o1.getId(), o2.getId());
                case 1:
                    return Long.compare(o2.getId(), o1.getId());
                case 2:
                    return Integer.compare(o1.getType(), o2.getType());
                case 3:
                    return Integer.compare(o2.getType(), o1.getType());
                case 4:
                    return o1.getFirstName().compareTo(o2.getFirstName());
                case 5:
                    return o2.getFirstName().compareTo(o1.getFirstName());
                case 6:
                    return o1.getLastName().compareTo(o2.getLastName());
                case 7:
                    return o2.getLastName().compareTo(o1.getLastName());

            }
            return Long.compare(o1.getId(), o2.getId());
        });
        return copy;
    }


    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(User model) {
        userRepository.delete(model);
    }


    @Override
    public Boolean isEmailFree(String email) {
        User user = userRepository.getUserByEmail(email);
        return user == null;
    }

    @Override
    public Boolean isEmailCorrect(String target) {
        return target.contains("@") && target.contains(".");
    }

    @Override
    public Boolean throwsErrors(User User, String pass2) {
        return pass2 == null || isEmailCorrect(User.getEmail()) && (User.getPhoneNumber().equals("") || isPhoneFree(User.getPhoneNumber())) && isEmailFree(User.getEmail()) && User.getPass().equals(pass2);
    }

    @Override
    public Boolean isPhoneFree(String phone) {
        User user = userRepository.getUserByPhoneNumber(phone);
        return user == null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}