package model;

import lombok.Data;
import lombok.Getter;

@Data
public class Order {
    private int id;
    private int totalFee;
    private int status;
    private String createTime;
}
