package com.mall.controller;

import com.mall.po.Item;
import com.mall.po.Result;
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
    public Result list(){
        log.info("Search all items");
        List<Item> itemList = itemService.list();
        return Result.success(itemList);
    }

    @GetMapping("/getItemById/{id}")
    public Result queryItemById(@PathVariable Integer id) {
        log.info("Search item by id={}", id);
        Item item = itemService.getItemById(id);
        return Result.success(item);
    }

    @GetMapping("/getItemByCategory/{category}")
    public Result queryItemByCategory(@PathVariable String category) {
        log.info("Search item by Category={}", category);
        List<Item> items = itemService.getItemByCategory(category);
        return Result.success(items);
    }

    @GetMapping("/getItemByPrices")
    public Result queryItemByPrices(@RequestParam int low, @RequestParam int high) {
        log.info("Searching items with price range: {} - {}", low, high);
        List<Item> items = itemService.getItemByPrices(low, high);
        return Result.success(items);
    }

}
