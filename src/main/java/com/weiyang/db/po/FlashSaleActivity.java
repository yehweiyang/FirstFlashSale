package com.weiyang.db.po;

import java.math.BigDecimal;
import java.util.Date;

public class FlashSaleActivity {
    private Long id;

    private String name;

    private Long commodityId;

    private BigDecimal oldPrice;

    private BigDecimal flashSalePrice;

    private Integer activityStatus;

    private Date startTime;

    private Date endTime;

    private Long totalStock;

    private Integer availableStock;

    private Long lockStock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getFlashSalePrice() {
        return flashSalePrice;
    }

    public void setFlashSalePrice(BigDecimal flashSalePrice) {
        this.flashSalePrice = flashSalePrice;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Long totalStock) {
        this.totalStock = totalStock;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Long getLockStock() {
        return lockStock;
    }

    public void setLockStock(Long lockStock) {
        this.lockStock = lockStock;
    }

    @Override
    public String toString() {
        return "FlashSaleActivity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", commodityId=" + commodityId +
                ", oldPrice=" + oldPrice +
                ", flashSalePrice=" + flashSalePrice +
                ", activityStatus=" + activityStatus +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalStock=" + totalStock +
                ", availableStock=" + availableStock +
                ", lockStock=" + lockStock +
                '}';
    }
}