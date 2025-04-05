package com.mall.service;

import com.mall.domains.dto.CartDTO;
import com.mall.domains.po.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getMyCart();

    void addToCart(CartDTO cartDTO);

    void updateCart(Cart cart);

    void removeById(Integer id);
}

