package com.mall.domains.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Integer id;
    private String name;
    private Integer price;
    private Integer stock;
    private String image;
    private String category;
    private Integer sold;
    private Boolean isAD;
}
