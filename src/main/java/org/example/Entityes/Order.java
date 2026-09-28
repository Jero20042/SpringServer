package org.example.Entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Long customerId;
    private Long productId;
    private Integer amount;
    private String productName;
    private Timestamp date;
}
