package com.mall.service;

import com.mall.domains.dto.CartDTO;
import com.mall.domains.po.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getMyCart();

    int addToCart(CartDTO cartDTO);

    void updateCart(Cart cart);

    void removeByUserIdAndItemIds(Integer userId, List<Integer> itemIds);

    void removeById(Integer id);
}

