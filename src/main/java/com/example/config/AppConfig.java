package com.example.config;

import com.example.cart.CartLineInfo;
import com.example.image.Image;
import com.example.order.Order;
import com.example.product.Product;
import com.example.token.TokenCookies;
import com.example.user.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@PropertySource("classpath:appplication.properties")
@EnableJpaRepositories(basePackages = "com.example.repo")
@EntityScan(basePackageClasses = {User.class, TokenCookies.class, Product.class, Image.class, Order.class, CartLineInfo.class})
@EnableTransactionManagement
public class AppConfig implements WebMvcConfigurer {

}
