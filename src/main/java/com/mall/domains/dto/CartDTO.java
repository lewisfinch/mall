package com.mall.domains.dto;

import lombok.Data;

@Data
public class CartDTO {
    private Integer itemId;
    private String itemName;
    private Integer price;
    private String itemImage;
}
