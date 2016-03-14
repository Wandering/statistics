package cn.thinkjoy.jx.statistics.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by Mike on 2015/12/18.
 */
public class ArrearInfo extends BaseDomain{
    private String phoneNumber;
    private String deviceStatus;
    private String productInstance;
    private String commodityInstance;
    private String goodsName;
    private int arrearAge;
    private float arrearAmount;
    private String effective;
    private String charge;
    private String billingType;
    private String billingLabel;
    private Long userId;
    private String userName;
    private Long areaId;
    private String areaName;
    private Long schoolId;
    private String shoolName;
    private Long classId;
    private String className;
    private Long studentId;
    private String studentName;
    private String payNumber;
    private String loginNumber;
    private Long templateId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getArrearAge() {
        return arrearAge;
    }

    public void setArrearAge(int arrearAge) {
        this.arrearAge = arrearAge;
    }

    public float getArrearAmount() {
        return arrearAmount;
    }

    public void setArrearAmount(float arrearAmount) {
        this.arrearAmount = arrearAmount;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public String getBillingLabel() {
        return billingLabel;
    }

    public void setBillingLabel(String billingLabel) {
        this.billingLabel = billingLabel;
    }

    public String getProductInstance() {
        return productInstance;
    }

    public void setProductInstance(String productInstance) {
        this.productInstance = productInstance;
    }

    public String getCommodityInstance() {
        return commodityInstance;
    }

    public void setCommodityInstance(String commodityInstance) {
        this.commodityInstance = commodityInstance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getShoolName() {
        return shoolName;
    }

    public void setShoolName(String shoolName) {
        this.shoolName = shoolName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(String loginNumber) {
        this.loginNumber = loginNumber;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
