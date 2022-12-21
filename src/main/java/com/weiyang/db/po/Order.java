package com.weiyang.db.po;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Long id;

    private String orderNo;

    private Integer orderStatus;

    private Long flashsaleActivityId;

    private Long userId;

    private Long orderAmount;

    private Date createTime;

    private Date payTime;


}