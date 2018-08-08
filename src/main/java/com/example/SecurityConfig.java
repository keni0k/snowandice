package com.example;

import com.example.repo.UserRepository;
import com.example.token.TokenService;
import com.example.user.UserServiceImpl;
import com.example.utils.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl personService;

    private final TokenService repository;

    @Autowired
    public SecurityConfig(UserRepository personRepository, TokenService repository) {
        personService = new UserServiceImpl(personRepository);
        this.repository = repository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new CustomAuthenticationProvider(personService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/", "/index", "/resources/**",
                        "/products", "/css/**", "/js/**", "/img/**", "/orders/cart",
                        "/orders/checkout", "/orders/add", "/contacts", "/ship_and_pay", "/privacy_policy",
                        "/exchange_and_returns", "/fix").permitAll()
                .antMatchers("/users/registration").anonymous()
                .antMatchers("/users/account", "/users/edit_data", "/users/edit_address").hasAnyRole("ADMIN", "USER")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().defaultSuccessUrl("/users/account", false)
                .loginPage("/users/login").failureUrl("/users/login?error=true").permitAll()
                .and()
                .logout().logoutUrl("/users/logout").logoutSuccessUrl("/users/login").permitAll()
                .and()
                .rememberMe().tokenValiditySeconds(1209600).rememberMeParameter("remember-me").rememberMeCookieName("remember_me")
                .tokenRepository(repository)
                .and()
                .exceptionHandling().accessDeniedPage("/error/403")
                .and()
                .csrf().disable();
    }

}
