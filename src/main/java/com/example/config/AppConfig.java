package com.example.config;

import com.example.cart.CartLineInfo;
import com.example.models.Image;
import com.example.models.Product;
import com.example.models.TokenCookies;
import com.example.models.User;
import com.example.models.order.Order;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@PropertySource("classpath:appplication.properties")
@EnableJpaRepositories(basePackages = "com.example.repo")
@EntityScan(basePackageClasses = {User.class, TokenCookies.class, Product.class, Image.class, Order.class, CartLineInfo.class})
@EnableTransactionManagement
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
    }
}
