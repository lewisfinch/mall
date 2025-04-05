package com.mall.domains.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart{
    private Integer id;
    private Integer userId;
    private Integer itemId;
    private Integer itemNum;
    private String itemName;
    private Integer price;
    private String itemImage;
}
