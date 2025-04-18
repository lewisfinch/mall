package com.mall.mapper;

import com.mall.domains.po.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ItemMapper {

    @Select("SELECT * FROM item")
    List<Item> list();

    @Select("SELECT * FROM item WHERE id = #{id}")
    Item getItemById(Integer id);

    @Select({
            "<script>",
            "SELECT * FROM item WHERE id IN",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Item> getItemByIds(List<Integer> ids);

    @Select("SELECT * FROM item WHERE category = #{category}")
    List<Item> getItemByCategory(String category);

    @Select("SELECT * FROM item WHERE price BETWEEN #{low} AND #{high}")
    List<Item> getItemByPrices(int low, int high);

    @Select("SELECT * FROM item WHERE category = #{category} AND price BETWEEN #{low} AND #{high}")
    List<Item> getItemByCategoryAndPrices(String category, int low, int high);

    @Update("UPDATE item SET stock = stock - #{itemNum}, sold = sold + #{itemNum} WHERE id = #{itemId} AND stock >= #{itemNum}")
    int deductStock(Integer itemId, Integer itemNum);

    @Update("UPDATE item SET stock = stock + #{itemNum}, sold = sold - #{itemNum} WHERE id = #{itemId}")
    int recoverStock(Integer itemId, Integer itemNum);
}
