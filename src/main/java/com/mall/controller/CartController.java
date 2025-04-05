package com.mall.controller;

import com.mall.domains.dto.CartDTO;
import com.mall.domains.po.Cart;
import com.mall.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/myCart")
    public List<Cart> getMyCart(){
        return cartService.getMyCart();
    }

    @PostMapping("/addToCart")
    public void addToCart(@RequestBody CartDTO cartDTO){
        cartService.addToCart(cartDTO);
    }

    @PutMapping("/updateCart")
    public void updateCart(@RequestBody Cart cart){cartService.updateCart(cart); }

    @DeleteMapping("/deleteCart/{id}")
    public void deleteCartItem(@PathVariable("id") Integer id){
        cartService.removeById(id);
    }


}
