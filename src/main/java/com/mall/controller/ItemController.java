package com.mall.controller;

import com.mall.domains.po.Item;
import com.mall.domains.po.Result;
import com.mall.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/list")
    public List<Item> list(){
        log.info("Search all items");
        return itemService.list();
    }

    @GetMapping("/getItemById/{id}")
    public Item queryItemById(@PathVariable Integer id) {
        log.info("Search item by id={}", id);
        return itemService.getItemById(id);
    }

    @GetMapping("/getItemByIds")
    public List<Item> queryItemByIds(@RequestParam("ids") List<Integer> ids) {
        log.info("Search item by ids={}", ids);
        return itemService.getItemByIds(ids);
    }

    @GetMapping("/getItemByCategory/{category}")
    public List<Item> queryItemByCategory(@PathVariable String category) {
        log.info("Search item by Category={}", category);
        return itemService.getItemByCategory(category);
    }

    @GetMapping("/getItemByPrices")
    public List<Item> queryItemByPrices(@RequestParam int low, @RequestParam int high) {
        log.info("Searching items with price range: {} - {}", low, high);
        return itemService.getItemByPrices(low, high);
    }

}
