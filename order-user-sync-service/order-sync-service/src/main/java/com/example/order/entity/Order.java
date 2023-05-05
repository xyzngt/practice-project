package com.example.order.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Order class
 *
 * @author xyzngtt
 *  
 */
@Data
@Accessors(chain = true)
@Entity
public class Order extends Model<Order> {
    /** 订单id **/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    /** 订单编号 **/
    private String orderSn;
    /** 产品id **/
    private Long productId;
    /** 产品图片 **/
    private String productPic;
    /** 产品名称 **/
    private String productName;

    /** 销售价格 **/
    private BigDecimal productPrice;

    /** 购买数量 **/
    private Integer productQuantity;

    /** 商品分类id **/
    private Long productCategoryId;
}
