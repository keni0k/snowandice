package com.example.user;

import com.example.repo.UserRepository;

import javax.jws.soap.SOAPBinding;
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
    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User getByLoginOrEmail(String loginOrEmail) {
        loginOrEmail = loginOrEmail.toLowerCase();
        return userRepository.getUserByLoginOrEmail(loginOrEmail, loginOrEmail);
    }

    @Override
    public List<User> getByFilter(Integer type, String firstName, String lastName, String city, Integer sortBy) {

        if (type==null) type = -1;
        if (firstName==null) firstName = "";
        if (lastName==null) lastName = "";
        if (city==null) city="";
        List<User> list = getAll();

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
    public User getById(long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void editUser(User User) {
        userRepository.saveAndFlush(User);
    }


    @Override
    public Boolean isLoginFree(String login) {
        User user = userRepository.getUserByLogin(login);
        return user == null;
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
        return pass2 == null || isEmailCorrect(User.getEmail()) && isLoginFree(User.getLogin()) && (User.getPhoneNumber().equals("") || isPhoneFree(User.getPhoneNumber())) && isEmailFree(User.getEmail()) && User.getPass().equals(pass2);
    }

    @Override
    public Boolean isPhoneFree(String phone) {
        User user = userRepository.getUserByPhoneNumber(phone);
        return user == null;
    }


    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

}