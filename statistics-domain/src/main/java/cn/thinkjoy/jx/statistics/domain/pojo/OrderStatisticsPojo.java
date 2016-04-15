package cn.thinkjoy.jx.statistics.domain.pojo;

import java.io.Serializable;

/**
 * Created by yangguorong on 16/4/15.
 *
 * 订单统计
 */
public class OrderStatisticsPojo implements Serializable {

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门编码
     */
    private long departmentCode;

    /**
     * 等级
     */
    private String departmentLevel;

    /**
     * web售价
     */
    private float webPrice;

    /**
     * 微信售价
     */
    private float wechatPrice;

    /**
     * 出厂价
     */
    private float salePrice;

    /**
     * web销量
     */
    private float webVolume;

    /**
     * 微信销量
     */
    private float wechatVolume;

    /**
     * 网上收益
     */
    private float netIncome;

    /**
     * 未结算
     */
    private float notSettled;

    /**
     * 已结算
     */
    private float settled;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public long getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(long departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(String departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    public float getWebPrice() {
        return webPrice;
    }

    public void setWebPrice(float webPrice) {
        this.webPrice = webPrice;
    }

    public float getWechatPrice() {
        return wechatPrice;
    }

    public void setWechatPrice(float wechatPrice) {
        this.wechatPrice = wechatPrice;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public float getWebVolume() {
        return webVolume;
    }

    public void setWebVolume(float webVolume) {
        this.webVolume = webVolume;
    }

    public float getWechatVolume() {
        return wechatVolume;
    }

    public void setWechatVolume(float wechatVolume) {
        this.wechatVolume = wechatVolume;
    }

    public float getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(float netIncome) {
        this.netIncome = netIncome;
    }

    public float getNotSettled() {
        return notSettled;
    }

    public void setNotSettled(float notSettled) {
        this.notSettled = notSettled;
    }

    public float getSettled() {
        return settled;
    }

    public void setSettled(float settled) {
        this.settled = settled;
    }

}
