package com.mall.domains.po;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderDetail {
    private Integer id;
    private Integer orderId;
    private Integer itemId;
    private Integer itemNum;
    private String itemName;
    private Integer price;
    private String image;
}
