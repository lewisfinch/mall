package com.mall.service.Impl;

import com.mall.domains.po.OrderDetail;
import com.mall.mapper.ItemMapper;
import com.mall.domains.po.Item;
import com.mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper itemMapper;

    @Override
    public List<Item> list() {
        return itemMapper.list();
    }

    @Override
    public Item getItemById(Integer id) {
        return itemMapper.getItemById(id);
    }

    @Override
    public List<Item> getItemByIds(List<Integer> ids) {
        return itemMapper.getItemByIds(ids);
    }

    @Override
    public void deductStock(List<OrderDetail> orderDetails) {
        for(OrderDetail orderDetail : orderDetails) {
            int affected = itemMapper.deductStock(orderDetail.getItemId(), orderDetail.getItemNum());
            if (affected == 0) {
                throw new RuntimeException("Stock not enough, deduction failed, itemId=" + orderDetail.getItemId());
            }
        }
    }

    @Override
    public void recoverStock(List<OrderDetail> orderDetails) {
        for(OrderDetail orderDetail : orderDetails) {
            int affected = itemMapper.recoverStock(orderDetail.getItemId(), orderDetail.getItemNum());
            if (affected == 0) {
                throw new RuntimeException("Stock recover failed");
            }
        }
    }

    @Override
    public List<Item> getItemByCategory(String category) {
        return itemMapper.getItemByCategory(category);
    }

    @Override
    public List<Item> getItemByPrices(int low, int high) {
        return itemMapper.getItemByPrices(low, high);
    }

    @Override
    public List<Item> getItemByCategoryAndPrices(String category, int low, int high) {
        return itemMapper.getItemByCategoryAndPrices(category, low, high);
    }

}

