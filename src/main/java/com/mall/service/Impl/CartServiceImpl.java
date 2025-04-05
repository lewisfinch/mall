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
    public void addToCart(CartDTO cartDTO) {
        Integer userId = UserContext.getCurrentUser();
        if(checkItemExist(userId, cartDTO.getItemId())){
            cartMapper.updateNum(userId, cartDTO.getItemId());
            return;
        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemId(cartDTO.getItemId());
        cart.setItemName(cartDTO.getItemName());
        cart.setPrice(cartDTO.getPrice());
        cart.setItemImage(cartDTO.getItemImage());
        cart.setItemNum(1);
        cartMapper.addToCart(cart);
    }

    @Override
    public void updateCart(Cart cart){
        cart.setUserId(UserContext.getCurrentUser());
        cartMapper.updateCart(cart);
    }

    @Override
    public void removeById(Integer id) {
        cartMapper.removeById(id);
    }


    public boolean checkItemExist(Integer userId, Integer itemId) {
        return cartMapper.checkItemExist(userId, itemId) > 0;
    }
}
