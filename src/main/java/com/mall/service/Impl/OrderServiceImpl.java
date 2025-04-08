package com.mall.service.Impl;

import com.mall.domains.dto.OrderDTO;
import com.mall.domains.dto.OrderItemDTO;
import com.mall.domains.po.Item;
import com.mall.domains.po.Order;
import com.mall.domains.po.OrderDetail;
import com.mall.mapper.OrderMapper;
import com.mall.service.CartService;
import com.mall.service.ItemService;
import com.mall.service.OrderService;
import com.mall.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    @Override
    public Order getOrder(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public Integer createOrder(OrderDTO orderDTO) {
        System.out.println(orderDTO);
        Order order = new Order();

        List<OrderItemDTO> orderItems = orderDTO.getDetails();
        Map<Integer, Integer> itemMap = new HashMap<>();
        for(OrderItemDTO orderItem : orderItems) {
            itemMap.put(orderItem.getItemId(), orderItem.getNum());
        }
        System.out.println(itemMap);
        Set<Integer> itemIds = itemMap.keySet();
        List<Integer> itemIdList = new ArrayList<>(itemIds);
        List<Item> items = itemService.getItemByIds(itemIdList);

        if(items == null || items.size() < itemIdList.size()) {
            return -1;
        }

        int total = 0;
        for(Item item : items) {
            total += item.getPrice() * itemMap.get(item.getId());
        }

        order.setTotalFee(total);
        order.setUserId(UserContext.getCurrentUser());
        order.setStatus(1);
        order.setCreateTime(LocalDateTime.now().withNano(0));
        System.out.println(order.getCreateTime());
        System.out.println(order.getTotalFee());

        orderMapper.addOrder(order);
        Integer orderId = orderMapper.getOrderByUTF(order.getUserId(), order.getCreateTime(), order.getTotalFee());

        List<OrderDetail> orderDetails = new ArrayList<>();

        for(Item item : items) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setItemId(item.getId());
            orderDetail.setItemName(item.getName());
            orderDetail.setItemNum(itemMap.get(item.getId()));
            orderDetail.setPrice(item.getPrice() * orderDetail.getItemNum());
            orderDetail.setImage(item.getImage());
            orderDetails.add(orderDetail);
            orderMapper.addOrderDetail(orderDetail);
            cartService.removeById(item.getId());
        }

        try {
            itemService.deductStock(orderDetails);
        } catch (Exception e) {
            throw new RuntimeException("No enough Stock");
        }

        return order.getId();
    }

    @Override
    public void InsertOrderDetail(OrderDetail orderDetail) {
        orderMapper.addOrderDetail(orderDetail);
    }
}
