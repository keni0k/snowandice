package com.example.utils;

import com.example.jpa_services_impl.UserServiceImpl;
import com.example.models.User;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserServiceImpl personService;

    public CustomAuthenticationProvider(UserServiceImpl personService) {
        this.personService = personService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        if (authentication!=null) {
            String name = authentication.getName();
            // You can get the password here
            String password = authentication.getCredentials().toString();

            User user = personService.getByLoginOrEmail(name);

            // Your custom authentication logic here
            if (user != null) {
                if (user.getPass().equals(password)) {
                    List<GrantedAuthority> grantedAuths = new ArrayList<>();
                    grantedAuths.add(new SimpleGrantedAuthority(user.getRole()));
                    return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
                }
                else LoggerFactory.getLogger(CustomAuthenticationProvider.class).info(password);
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}