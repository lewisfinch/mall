package com.mall.mapper;

import com.mall.domains.po.Order;
import com.mall.domains.po.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Order getOrderById(Integer id);

    @Insert("INSERT INTO `order` (total_fee, user_id, status, create_time, pay_time, ship_time, cancel_time) " +
            "VALUES (#{totalFee}, #{userId}, #{status}, #{createTime}, #{payTime}, #{shipTime}, #{cancelTime})")
    int addOrder(Order order);

    @Select("SELECT id FROM `order` WHERE user_id = #{userId} AND create_time = #{createTime} AND total_fee = #{totalFee} LIMIT 1")
    Integer getOrderByUTF(Integer userId, LocalDateTime createTime, Integer totalFee);

    @Insert("INSERT INTO order_detail (order_id, item_id, item_num, item_name, price, image) " +
            "VALUES (#{orderId}, #{itemId}, #{itemNum}, #{itemName}, #{price}, #{image})")
    int addOrderDetail(OrderDetail orderDetail);

    @Update("UPDATE `order` SET status = #{status}, pay_time = #{payTime} WHERE id = #{orderId}")
    int updateOrderPayment(Integer orderId, Integer status, LocalDateTime payTime);

    @Update("UPDATE `order` SET status = #{status}, ship_time = #{shipTime} WHERE id = #{orderId}")
    int updateOrderArrival(Integer orderId, Integer status, LocalDateTime shipTime);

    @Update("UPDATE `order` SET status = #{status}, cancel_time = #{cancelTime} WHERE id = #{orderId}")
    int updateOrderClose(Integer orderId, Integer status, LocalDateTime cancelTime);

    @Select("SELECT * FROM order_detail WHERE order_id = #{orderId}")
    List<OrderDetail> getOrderDetail(Integer orderId);
}
