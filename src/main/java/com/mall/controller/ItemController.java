package com.mall.controller;

import com.mall.po.Result;
import com.mall.po.User;
import com.mall.service.ItemService;
import com.mall.service.UserService;
import com.mall.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ItemController {

    @Autowired
    private ItemService ItemService;

}
