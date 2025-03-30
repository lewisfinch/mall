package com.mall.service.Impl;

import com.mall.mapper.ItemMapper;
import com.mall.mapper.UserMapper;
import com.mall.po.Item;
import com.mall.po.User;
import com.mall.service.ItemService;
import com.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
    public List<Item> getItemByCategory(String category) {
        return itemMapper.getItemByCategory(category);
    }

    @Override
    public List<Item> getItemByPrices(int low, int high) {
        return itemMapper.getItemByPrices(low, high);
    }


}

