/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: system
 * $Id:  SettlementRecord.java 2016-04-19 20:10:50 $
 */



package cn.thinkjoy.jx.statistics.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class SettlementRecord extends BaseDomain{
    private Long requestTime;
    private Long departmentCode;
    private Integer type;
    private Double money;
    private Long operationUserId;

	public SettlementRecord(){
	}
    public void setRequestTime(Long value) {
        this.requestTime = value;
    }

    public Long getRequestTime() {
        return this.requestTime;
    }
    public void setDepartmentCode(Long value) {
        this.departmentCode = value;
    }

    public Long getDepartmentCode() {
        return this.departmentCode;
    }
    public void setType(Integer value) {
        this.type = value;
    }

    public Integer getType() {
        return this.type;
    }
    public void setMoney(Double value) {
        this.money = value;
    }

    public Double getMoney() {
        return this.money;
    }
    public void setOperationUserId(Long value) {
        this.operationUserId = value;
    }

    public Long getOperationUserId() {
        return this.operationUserId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RequestTime",getRequestTime())
			.append("DepartmentCode",getDepartmentCode())
			.append("Type",getType())
			.append("Money",getMoney())
			.append("OperationUserId",getOperationUserId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SettlementRecord == false) return false;
		if(this == obj) return true;
		SettlementRecord other = (SettlementRecord)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

