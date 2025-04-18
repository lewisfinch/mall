package com.mall.mapper;

import com.mall.domains.po.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Select("SELECT * FROM cart WHERE user_id = #{userId}")
    List<Cart> getMyCart(Integer userId);

    @Insert("INSERT INTO cart (user_id, item_id, item_num, item_name, price, item_image) " +
            "VALUES (#{userId}, #{itemId}, #{itemNum}, #{itemName}, #{price}, #{itemImage})")
    int addToCart(Cart cart);

    @Update("UPDATE cart SET item_num = #{itemNum}, item_name = #{itemName}, price = #{price}, item_image = #{itemImage} WHERE item_id = #{itemId} AND user_id = #{userId}")
    int updateCart(Cart cart);

    @Update("UPDATE cart SET item_num = item_num + 1 WHERE user_id = #{userId} AND item_id = #{itemId}")
    int updateNum(Integer userId, Integer itemId);

    @Select("SELECT COUNT(1) FROM cart WHERE user_id = #{userId} AND item_id = #{itemId}")
    Integer checkItemExist(Integer userId, Integer itemId);

    @Delete("DELETE FROM cart WHERE id = #{id}")
    int removeById(Integer id);
}
