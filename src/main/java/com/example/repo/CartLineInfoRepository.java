package com.example.repo;

import com.example.models.order.CartLineInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartLineInfoRepository extends JpaRepository<CartLineInfo, Long> {

    List<CartLineInfo> getCartLineInfosByOrderId(long orderId);

}