package com.mall.service;

import com.mall.domains.po.Item;
import com.mall.domains.po.OrderDetail;

import java.util.List;

public interface ItemService {

    List<Item> list();

    Item getItemById(Integer id);

    List<Item> getItemByCategory(String category);

    List<Item> getItemByPrices(int low, int high);

    List<Item> getItemByCategoryAndPrices(String category, int low, int high);

    List<Item> getItemByIds(List<Integer> ids);

    void deductStock(List<OrderDetail> orderDetails);

    void recoverStock(List<OrderDetail> orderDetails);
}

