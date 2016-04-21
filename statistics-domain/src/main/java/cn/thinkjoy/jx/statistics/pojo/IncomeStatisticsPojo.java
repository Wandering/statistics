package cn.thinkjoy.jx.statistics.pojo;

import java.io.Serializable;

/**
 * Created by yangguorong on 16/4/16.
 *
 * 普通用户收益统计
 */
public class IncomeStatisticsPojo implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 注册地址
     */
    private String registAddress;

    /**
     * 一级奖励个数
     */
    private int firstLevelCount;

    /**
     * 二级奖励个数
     */
    private int secondLevelCount;
    /**
     * 总收益
     */
    private double allIncome;

    /**
     * 未结算
     */
    private double notSettled;

    /**
     * 已结算
     */
    private double settled;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRegistAddress() {
        return registAddress;
    }

    public void setRegistAddress(String registAddress) {
        this.registAddress = registAddress;
    }

    public int getFirstLevelCount() {
        return firstLevelCount;
    }

    public void setFirstLevelCount(int firstLevelCount) {
        this.firstLevelCount = firstLevelCount;
    }

    public int getSecondLevelCount() {
        return secondLevelCount;
    }

    public void setSecondLevelCount(int secondLevelCount) {
        this.secondLevelCount = secondLevelCount;
    }

    public double getAllIncome() {
        return allIncome;
    }

    public void setAllIncome(double allIncome) {
        this.allIncome = allIncome;
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
