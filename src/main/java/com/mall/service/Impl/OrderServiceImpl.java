package com.mall.service.Impl;

import com.mall.domains.dto.OrderDTO;
import com.mall.domains.dto.OrderItemDTO;
import com.mall.domains.po.Item;
import com.mall.domains.po.Order;
import com.mall.domains.po.OrderDetail;
import com.mall.enums.OrderStatus;
import com.mall.mapper.OrderMapper;
import com.mall.service.CartService;
import com.mall.service.ItemService;
import com.mall.service.OrderService;
import com.mall.service.UserService;
import com.mall.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    UserService userService;

    @Override
    public Order getOrder(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    @Transactional
    public Integer createOrder(OrderDTO orderDTO) {

        // 1. Collect OrderItems
        List<OrderItemDTO> orderItems = orderDTO.getDetails();

        // 2. Create ItemId : ItemNum map
        Map<Integer, Integer> itemMap = new HashMap<>();
        for(OrderItemDTO orderItem : orderItems) {
            itemMap.put(orderItem.getItemId(), orderItem.getNum());
        }

        // 3. Create ItemId List
        Set<Integer> itemIds = itemMap.keySet();
        List<Integer> itemIdList = new ArrayList<>(itemIds);

        // 4. Get Item List
        List<Item> items = itemService.getItemByIds(itemIdList);

        // 5. Check Items NULL or ERROR
        if(items == null || items.size() < itemIdList.size()) {
            return -1;
        }

        // 6. Calculate Total Price
        int total = 0;
        for(Item item : items) {
            total += item.getPrice() * itemMap.get(item.getId());
        }

        // 7. Build Order
        Order order = new Order();
        order.setTotalFee(total);
        order.setUserId(UserContext.getCurrentUser());
        order.setStatus(OrderStatus.WAIT_PAY.getValue());
        order.setCreateTime(LocalDateTime.now().withNano(0));
        orderMapper.addOrder(order);

        // 8. Get OrderId
        Integer orderId = orderMapper.getOrderByUTF(order.getUserId(), order.getCreateTime(), order.getTotalFee());

        // 9. Insert OrderDetails and Remove CartItems
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

        // 10. Deduct Stock
        itemService.deductStock(orderDetails);

        // 11. Deduct Balance
        userService.deductBalance(UserContext.getCurrentUser(), total);
        orderMapper.updateOrderPayment(orderId, OrderStatus.PAID_SHIPPING.getValue(), LocalDateTime.now());

        // 12. Return Order
        return order.getId();
    }

    @Override
    public void InsertOrderDetail(OrderDetail orderDetail) {
        orderMapper.addOrderDetail(orderDetail);
    }
}
