package com.mall.service.Impl;

import com.mall.domains.dto.CartDTO;
import com.mall.mapper.CartMapper;
import com.mall.domains.po.Cart;
import com.mall.service.CartService;
import com.mall.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    @Override
    public List<Cart> getMyCart() {
        Integer userId = UserContext.getCurrentUser();
        return cartMapper.getMyCart(userId);
    }

    @Override
    public int addToCart(CartDTO cartDTO) {
        Integer userId = UserContext.getCurrentUser();
        if (checkItemExist(userId, cartDTO.getItemId())) {
            int affected = cartMapper.updateNum(userId, cartDTO.getItemId());
            if (affected == 0) {
                throw new RuntimeException("Cart update Failed");
            }
            // 🔥 After updating, fetch the cart row to get the cart ID
            Cart cart = cartMapper.findCartByUserIdAndItemId(userId, cartDTO.getItemId());
            if (cart == null) {
                throw new RuntimeException("Cart not found after update");
            }
            return cart.getId();
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemId(cartDTO.getItemId());
        cart.setItemName(cartDTO.getItemName());
        cart.setPrice(cartDTO.getPrice());
        cart.setItemImage(cartDTO.getItemImage());
        cart.setItemNum(cartDTO.getItemNum());
        int affected = cartMapper.addToCart(cart);
        if (affected == 0) {
            throw new RuntimeException("Cart insertion Failed");
        }
        return cart.getId();
    }

    @Override
    public void updateCart(Cart cart) {
        cart.setUserId(UserContext.getCurrentUser());
        int affected = cartMapper.updateCart(cart);
        if (affected == 0) {
            throw new RuntimeException("Cart update Failed");
        }
    }

    @Override
    public void removeByUserIdAndItemIds(Integer userId, List<Integer> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            return;
        }
        int affected = cartMapper.removeByUserIdAndItemIds(userId, itemIds);
        if (affected == 0) {
            throw new RuntimeException("Cart batch remove failed");
        }
    }

    @Override
    public void removeById(Integer id) {
        int affected = cartMapper.removeById(id);
        if (affected == 0) {
            throw new RuntimeException("Cart Remove Failed");
        }
    }

    public boolean checkItemExist(Integer userId, Integer itemId) {
        return cartMapper.checkItemExist(userId, itemId) > 0;
    }
}
