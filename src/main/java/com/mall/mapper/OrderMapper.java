package com.mall.mapper;

import com.mall.domains.po.Order;
import com.mall.domains.po.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Order getOrderById(Integer id);

    @Insert("INSERT INTO `order` (total_fee, user_id, status, create_time, pay_time, ship_time, close_time) " +
            "VALUES (#{totalFee}, #{userId}, #{status}, #{createTime}, #{payTime}, #{shipTime}, #{closeTime})")
    void addOrder(Order order);

    @Select("SELECT id FROM `order` WHERE user_id = #{userId} AND create_time = #{createTime} AND total_fee = #{totalFee} LIMIT 1")
    Integer getOrderByUTF(Integer userId, LocalDateTime createTime, Integer totalFee);

    @Insert("INSERT INTO order_detail (order_id, item_id, item_num, item_name, price, image) " +
            "VALUES (#{orderId}, #{itemId}, #{itemNum}, #{itemName}, #{price}, #{image})")
    void addOrderDetail(OrderDetail orderDetail);
}
