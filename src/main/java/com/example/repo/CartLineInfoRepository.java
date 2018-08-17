package com.example.repo;

import com.example.cart.CartLineInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartLineInfoRepository extends JpaRepository<CartLineInfo, Long> {

    CartLineInfo getCartLineInfoById (long id);

    List<CartLineInfo> getCartLineInfosByOrderId(long orderId);

    List<CartLineInfo> getCartLineInfosByProductId(long productId);
}
