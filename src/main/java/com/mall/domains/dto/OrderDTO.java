package com.mall.domains.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private List<OrderItemDTO> details;
}
