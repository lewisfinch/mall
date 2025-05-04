package com.mall.controller;

import com.mall.domains.dto.CartDTO;
import com.mall.domains.po.Cart;
import com.mall.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/myCart")
    public List<Cart> getMyCart() {
        return cartService.getMyCart();
    }

    @PostMapping("/addToCart")
    public Map<String, Object> addToCart(@RequestBody CartDTO cartDTO) {
        int cartId = cartService.addToCart(cartDTO); // service should return inserted id
        Map<String, Object> res = new HashMap<>();
        res.put("id", cartId);
        return res;
    }

    @PutMapping("/updateCart")
    public void updateCart(@RequestBody Cart cart) {
        cartService.updateCart(cart);
    }

    @DeleteMapping("/deleteCart/{id}")
    public void deleteCartItem(@PathVariable("id") Integer id){
        cartService.removeById(id);
    }

    @DeleteMapping("/deleteCartBatch")
    public void deleteCartItems(@RequestParam("userId") Integer userId,
                                @RequestBody List<Integer> itemIds) {
        cartService.removeByUserIdAndItemIds(userId, itemIds);
    }
}
