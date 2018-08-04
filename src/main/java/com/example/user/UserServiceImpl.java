package com.example.user;

import com.example.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Keni0k on 25.07.2017.
 */

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByToken(String token) {
        Iterable<User> list = userRepository.findAll();
        for (User p : list)
            if (p.getToken().equals(token))
                return p;
        return null;
    }

    @Override
    public User getByEmail(String email) {
        Iterable<User> list = userRepository.findAll();
        for (User p : list)
            if (p.getEmail().equals(email))
                return p;
        return null;
    }

    public User getByLoginOrEmail(String loginOrEmail) {
        Iterable<User> list = userRepository.findAll();
        for (User p : list)
            if (p.getLogin().toLowerCase().equals(loginOrEmail.toLowerCase()))
                return p;
            else if (p.getEmail().toLowerCase().equals(loginOrEmail.toLowerCase()))
                return p;
        return null;
    }

    @Override
    public List<User> getByFilter(Integer type, String firstName, String lastName, String city, Integer sortBy) {

        if (type==null) type = -1;
        if (firstName==null) firstName = "";
        if (lastName==null) lastName = "";
        if (city==null) city="";
        Iterable<User> list = getAll();

        List<User> copy = new ArrayList<>();
        for (User aList : list) {
            boolean isEqualsType = aList.getType() == type || type == -1;
            boolean isContainsFirstName = aList.getFirstName().toLowerCase().contains(firstName.toLowerCase());
            boolean isContainsLastName = aList.getLastName().toLowerCase().contains(lastName.toLowerCase());
            boolean isContainsCity = aList.getCity().toLowerCase().contains(city.toLowerCase());
            if (isEqualsType && isContainsFirstName && isContainsLastName && isContainsCity)
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
                case 8:
                    return o1.getCity().compareTo(o2.getCity());
                case 9:
                    return o2.getCity().compareTo(o1.getCity());

            }
            return Long.compare(o1.getId(), o2.getId());
        });
        return copy;
    }


    @Override
    public Optional<User> getById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User editUser(User User) {
        return userRepository.save(User);
    }


    @Override
    public Boolean isLoginFree(String login) {
        Iterable<User> list = userRepository.findAll();
        boolean isFree = true;
        for (User p : list)
            if (p.getLogin().equals(login))
                isFree = false;
        return isFree;
    }

    @Override
    public Boolean isEmailFree(String email) {
        Iterable<User> list = userRepository.findAll();
        boolean isFree = true;
        for (User p : list)
            if (p.getEmail().equals(email))
                isFree = false;
        return isFree;
    }

    @Override
    public Boolean isEmailCorrect(String target) {
        return target.contains("@") && target.contains(".");
    }

    @Override
    public Boolean throwsErrors(User User, String pass2) {
        return pass2 == null || isEmailCorrect(User.getEmail()) && isLoginFree(User.getLogin()) && (User.getPhoneNumber().equals("") || isPhoneFree(User.getPhoneNumber())) && isEmailFree(User.getEmail()) && User.getPass().equals(pass2);
    }

    @Override
    public Boolean isPhoneFree(String phone) {
        Iterable<User> list = getAll();
        boolean isFree = true;
        for (User p : list)
            if (p.getPhoneNumber().equals(phone))
                isFree = false;
        return isFree;
    }


    @Override
    public Boolean authorization(String login, String pass) {
        login = login.toLowerCase();
        Iterable<User> list = userRepository.findAll();
        for (User p : list)
            if (p.getLogin().equals(login) || p.getEmail().equals(login))
                if (p.getPass().equals(pass))
                    return true;
        return false;
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

}