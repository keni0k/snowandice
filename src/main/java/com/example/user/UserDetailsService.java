package com.example.user;

import com.example.jpa_services_impl.UserServiceImpl;
import com.example.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserServiceImpl userService;

    public UserDetailsService (UserRepository userRepository){
        this.userService = new UserServiceImpl(userRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userService.getByLoginOrEmail(s);
    }
}
