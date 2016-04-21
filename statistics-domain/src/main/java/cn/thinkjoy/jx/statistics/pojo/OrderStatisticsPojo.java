package cn.thinkjoy.jx.statistics.pojo;

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
    private double webPrice;

    /**
     * 微信售价
     */
    private double wechatPrice;

    /**
     * 出厂价
     */
    private double salePrice;

    /**
     * web销量
     */
    private int webSaleCount;

    /**
     * 微信销量
     */
    private int wechatSaleCount;

    /**
     * 网上收益
     */
    private double netIncome;

    /**
     * 未结算
     */
    private double notSettled;

    /**
     * 已结算
     */
    private double settled;

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

    public double getWebPrice() {
        return webPrice;
    }

    public void setWebPrice(double webPrice) {
        this.webPrice = webPrice;
    }

    public double getWechatPrice() {
        return wechatPrice;
    }

    public void setWechatPrice(double wechatPrice) {
        this.wechatPrice = wechatPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getWebSaleCount() {
        return webSaleCount;
    }

    public void setWebSaleCount(int webSaleCount) {
        this.webSaleCount = webSaleCount;
    }

    public int getWechatSaleCount() {
        return wechatSaleCount;
    }

    public void setWechatSaleCount(int wechatSaleCount) {
        this.wechatSaleCount = wechatSaleCount;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }

    public double getNotSettled() {
        return notSettled;
    }

    public void setNotSettled(double notSettled) {
        this.notSettled = notSettled;
    }

    public double getSettled() {
        return settled;
    }

    public void setSettled(double settled) {
        this.settled = settled;
    }
}
