package com.example.business.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * OrderReq class
 *
 * @author xyzngtt
 *
 */
@Data
public class OrderReq {
    private Long userId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
}
