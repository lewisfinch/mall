package com.mall.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    WAIT_PAY(1, "Wait for Payment"),
    PAID_SHIPPING(2, "Paid, Wait for Delivery"),
    CANCELED(3, "Canceled and Refunded"),
    CLOSED(4, "Closed"),
    ;
    private final int value;
    private final String desc;

    OrderStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public boolean equalsValue(Integer value){
        if (value == null) {
            return false;
        }
        return getValue() == value;
    }
}