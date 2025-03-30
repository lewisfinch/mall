package com.mall.mapper;

import com.mall.po.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemMapper {

    @Select("SELECT * FROM item")
    List<Item> list();

    @Select("SELECT * FROM item WHERE id = #{id}")
    Item getItemById(Integer id);

    @Select("SELECT * FROM item WHERE category = #{category}")
    List<Item> getItemByCategory(String category);

    @Select("SELECT * FROM item WHERE price BETWEEN #{low} AND #{high}")
    List<Item> getItemByPrices(int low, int high);
}
