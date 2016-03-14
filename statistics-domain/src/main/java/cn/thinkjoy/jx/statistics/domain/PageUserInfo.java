package cn.thinkjoy.jx.statistics.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by zl on 2015/12/22.
 */
public class PageUserInfo extends BaseDomain {
    //用户总数
    private int userTotalNumber;
    //收费用户数
    private int userTollNumber;
    //有效用户数
    private int userEffective;
    //区域名称
    private String areaName;
    //区域ID
    private Long areaId;
    //导入记录ID
    private Long templateId;

    public int getUserTotalNumber() {
        return userTotalNumber;
    }

    public void setUserTotalNumber(int userTotalNumber) {
        this.userTotalNumber = userTotalNumber;
    }

    public int getUserTollNumber() {
        return userTollNumber;
    }

    public void setUserTollNumber(int userTollNumber) {
        this.userTollNumber = userTollNumber;
    }

    public int getUserEffective() {
        return userEffective;
    }

    public void setUserEffective(int userEffective) {
        this.userEffective = userEffective;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
