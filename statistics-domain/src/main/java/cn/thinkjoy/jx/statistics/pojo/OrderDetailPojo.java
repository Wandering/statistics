package cn.thinkjoy.jx.statistics.pojo;

import java.io.Serializable;

/**
 * Created by yangguorong on 16/4/15.
 *
 * 订单详情
 */
public class OrderDetailPojo implements Serializable {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 付费渠道
     */
    private String channle;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 注册地址-->用户收货地址  modify 2016.06.18
     */
    private String registAddress;

    /**
     * 购买数量
     */
    private int goodsCount;

    /**
     * 创建时间
     */
    private long createDate;

    /**
     * 发货状态  0：未发货  1：已发货
     */
    private int handleState;

    /**
     * 奖励等级 1：一级奖励  2：二级奖励
     */
    private int rewardLevel;

    /**
     * 奖励金额
     */
    private double rewardMoney;

    /**
     * 套餐类型 1:金榜登科  2:状元及第  3:金榜题名
     */
    private int productType;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getChannle() {
        return channle;
    }

    public void setChannle(String channle) {
        this.channle = channle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getHandleState() {
        return handleState;
    }

    public void setHandleState(int handleState) {
        this.handleState = handleState;
    }

    public int getRewardLevel() {
        return rewardLevel;
    }

    public void setRewardLevel(int rewardLevel) {
        this.rewardLevel = rewardLevel;
    }

    public double getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(double rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }
}
