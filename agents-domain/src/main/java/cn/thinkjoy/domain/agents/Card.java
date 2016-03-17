/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: grab
 * $Id:  Card.java 2016-03-16 10:13:08 $
 */





package cn.thinkjoy.domain.agents;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class Card extends CreateBaseDomain<Long>{
    /** 货号 */
    private String goodsNumber;
    /** 卡号 */
    private String cardNumber;
    /** 密码 */
    private String password;
    /**  */
    private Long userId;
    /** 卡号类型，1为正式，2为演示（贾静静用），3为测试（杨甜用）,4为体验（薛延松用） */
    private String cardType;
    /** 有效结束日期 */
    private Long endDate;
    /** 区域Id（不使用） */
    private Long areaId;
    /** 省级出库时间，同时也是市级入库时间 */
    private Long outputDate1;
    /** 市级出库时间，同时也是县级入库时间 */
    private Long outputDate2;
    /** 县级出库时间 */
    private Long outputDate3;
    /** 激活日期 */
    private Long activeDate;

	public Card(){
	}
    public void setGoodsNumber(String value) {
        this.goodsNumber = value;
    }

    public String getGoodsNumber() {
        return this.goodsNumber;
    }
    public void setCardNumber(String value) {
        this.cardNumber = value;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }
    public void setPassword(String value) {
        this.password = value;
    }

    public String getPassword() {
        return this.password;
    }
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setCardType(String value) {
        this.cardType = value;
    }

    public String getCardType() {
        return this.cardType;
    }
    public void setEndDate(Long value) {
        this.endDate = value;
    }

    public Long getEndDate() {
        return this.endDate;
    }
    public void setAreaId(Long value) {
        this.areaId = value;
    }

    public Long getAreaId() {
        return this.areaId;
    }
    public void setOutputDate1(Long value) {
        this.outputDate1 = value;
    }

    public Long getOutputDate1() {
        return this.outputDate1;
    }
    public void setOutputDate2(Long value) {
        this.outputDate2 = value;
    }

    public Long getOutputDate2() {
        return this.outputDate2;
    }
    public void setOutputDate3(Long value) {
        this.outputDate3 = value;
    }

    public Long getOutputDate3() {
        return this.outputDate3;
    }
    public void setActiveDate(Long value) {
        this.activeDate = value;
    }

    public Long getActiveDate() {
        return this.activeDate;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("GoodsNumber",getGoodsNumber())
			.append("CardNumber",getCardNumber())
			.append("Password",getPassword())
			.append("UserId",getUserId())
			.append("Status",getStatus())
			.append("CardType",getCardType())
			.append("EndDate",getEndDate())
			.append("Creator",getCreator())
			.append("CreateDate",getCreateDate())
			.append("LastModifier",getLastModifier())
			.append("LastModDate",getLastModDate())
			.append("AreaId",getAreaId())
			.append("OutputDate1",getOutputDate1())
			.append("OutputDate2",getOutputDate2())
			.append("OutputDate3",getOutputDate3())
			.append("ActiveDate",getActiveDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Card == false) return false;
		if(this == obj) return true;
		Card other = (Card)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

