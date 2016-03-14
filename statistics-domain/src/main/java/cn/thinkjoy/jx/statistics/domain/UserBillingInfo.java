package cn.thinkjoy.jx.statistics.domain;

/**
 * Created by Mike on 2015/12/16.
 */
public class UserBillingInfo {
    private String phoneNumber;
    private String deviceStatus;
    private String goodsName;
    private String arrearAge;
    private String arrearAmount;
    private String billingType;
    private String chargeLabel;

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

    public String getArrearAge() {
        return arrearAge;
    }

    public void setArrearAge(String arrearAge) {
        this.arrearAge = arrearAge;
    }

    public String getArrearAmount() {
        return arrearAmount;
    }

    public void setArrearAmount(String arrearAmount) {
        this.arrearAmount = arrearAmount;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public String getChargeLabel() {
        return chargeLabel;
    }

    public void setChargeLabel(String chargeLabel) {
        this.chargeLabel = chargeLabel;
    }
}
