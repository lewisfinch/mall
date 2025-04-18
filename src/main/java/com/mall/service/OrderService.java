package com.mall.service;

import com.mall.domains.dto.OrderDTO;
import com.mall.domains.po.Order;
import com.mall.domains.po.OrderDetail;

import java.util.List;

public interface OrderService {

    Order getOrder(Integer orderId);

    Integer createOrder(OrderDTO orderDTO);

    void confirmRecipt(Integer orderId);

    void cancelOrder(Integer orderId);
}
