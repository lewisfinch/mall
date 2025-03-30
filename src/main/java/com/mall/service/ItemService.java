package com.mall.service;

import com.mall.po.Item;
import com.mall.po.User;

import java.util.List;

public interface ItemService {

    List<Item> list();

    Item getItemById(Integer id);

    List<Item> getItemByCategory(String category);

    List<Item> getItemByPrices(int low, int high);
}

