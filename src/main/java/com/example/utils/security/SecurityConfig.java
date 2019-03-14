package com.example.utils.security;

import com.example.jpa_services_impl.UserServiceImpl;
import com.example.repo.UserRepository;
import com.example.utils.CustomAuthenticationProvider;
import com.example.utils.security.token.TokenService;
import com.example.utils.security.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl personService;
    private UserDetailsService userDetailsService;
    private final TokenService repository;

    @Autowired
    public SecurityConfig(UserRepository personRepository, TokenService repository) {
        personService = new UserServiceImpl(personRepository);
        userDetailsService = new UserDetailsService(personRepository);
        this.repository = repository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new CustomAuthenticationProvider(personService));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/", "/index",
                        "/contacts", "/privacy_policy", "/exchange_and_returns",
                        "/fix", "/orders/widget",
                        "/actuator/*", "/instances", "/healh", "/actuator", "/admin/*").permitAll()
                .antMatchers(HttpMethod.POST, "/actuator").permitAll()
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
                .rememberMe().tokenValiditySeconds(1209600).rememberMeParameter("remember-me").rememberMeCookieName("remember_me").userDetailsService(userDetailsService)
                .tokenRepository(repository)
                .and()
                .exceptionHandling().accessDeniedPage("/error/403")
                .and()
                .csrf().disable();
    }

}
