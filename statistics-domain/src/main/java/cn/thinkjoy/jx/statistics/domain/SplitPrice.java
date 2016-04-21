/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: system
 * $Id:  SplitPrice.java 2016-04-16 12:41:14 $
 */



package cn.thinkjoy.jx.statistics.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class SplitPrice extends BaseDomain{
    private Long userId;
    private String userPhone;
    private Integer rewardLevel;
    private Integer price;
    private Integer status;
    private String orderNo;
    private Integer type;
    private Long createTime;

	public SplitPrice(){
	}
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setUserPhone(String value) {
        this.userPhone = value;
    }

    public String getUserPhone() {
        return this.userPhone;
    }
    public void setRewardLevel(Integer value) {
        this.rewardLevel = value;
    }

    public Integer getRewardLevel() {
        return this.rewardLevel;
    }
    public void setPrice(Integer value) {
        this.price = value;
    }

    public Integer getPrice() {
        return this.price;
    }
    public void setOrderNo(String value) {
        this.orderNo = value;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setCreateTime(Long value) {
        this.createTime = value;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("UserPhone",getUserPhone())
			.append("RewardLevel",getRewardLevel())
			.append("Price",getPrice())
			.append("OrderNo",getOrderNo())
			.append("Type",getType())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SplitPrice == false) return false;
		if(this == obj) return true;
		SplitPrice other = (SplitPrice)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

