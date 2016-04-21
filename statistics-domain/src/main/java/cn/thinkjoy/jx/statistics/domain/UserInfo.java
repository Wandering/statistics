/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: system
 * $Id:  UserInfo.java 2016-04-20 11:31:25 $
 */



package cn.thinkjoy.jx.statistics.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class UserInfo extends BaseDomain{
    private String token;
    private String name;
    private String icon;
    private String provinceId;
    private String cityId;
    private String countyId;
    private String schoolName;
    private Long birthdayDate;
    private Integer sex;
    private Integer subjectType;
    private String mail;
    private String qq;

	public UserInfo(){
	}
    public void setToken(String value) {
        this.token = value;
    }

    public String getToken() {
        return this.token;
    }
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setIcon(String value) {
        this.icon = value;
    }

    public String getIcon() {
        return this.icon;
    }
    public void setProvinceId(String value) {
        this.provinceId = value;
    }

    public String getProvinceId() {
        return this.provinceId;
    }
    public void setCityId(String value) {
        this.cityId = value;
    }

    public String getCityId() {
        return this.cityId;
    }
    public void setCountyId(String value) {
        this.countyId = value;
    }

    public String getCountyId() {
        return this.countyId;
    }
    public void setSchoolName(String value) {
        this.schoolName = value;
    }

    public String getSchoolName() {
        return this.schoolName;
    }
    public void setBirthdayDate(Long value) {
        this.birthdayDate = value;
    }

    public Long getBirthdayDate() {
        return this.birthdayDate;
    }
    public void setSex(Integer value) {
        this.sex = value;
    }

    public Integer getSex() {
        return this.sex;
    }
    public void setSubjectType(Integer value) {
        this.subjectType = value;
    }

    public Integer getSubjectType() {
        return this.subjectType;
    }
    public void setMail(String value) {
        this.mail = value;
    }

    public String getMail() {
        return this.mail;
    }
    public void setQq(String value) {
        this.qq = value;
    }

    public String getQq() {
        return this.qq;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Token",getToken())
			.append("Name",getName())
			.append("Icon",getIcon())
			.append("ProvinceId",getProvinceId())
			.append("CityId",getCityId())
			.append("CountyId",getCountyId())
			.append("SchoolName",getSchoolName())
			.append("BirthdayDate",getBirthdayDate())
			.append("Sex",getSex())
			.append("SubjectType",getSubjectType())
			.append("Mail",getMail())
			.append("Qq",getQq())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserInfo == false) return false;
		if(this == obj) return true;
		UserInfo other = (UserInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

