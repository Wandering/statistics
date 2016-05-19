package cn.thinkjoy.jx.statistics.pojo;

import java.io.Serializable;

/**
 * Created by yangguorong on 16/5/19.
 */
public class ProductSaleDetailPojo implements Serializable {

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 售价
     */
    private double salePrice;

    /**
     * 出厂价
     */
    private double pickupPrice;

    /**
     * web销量
     */
    private int webSaleCount;

    /**
     * 微信销量
     */
    private int wechatSaleCount;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getPickupPrice() {
        return pickupPrice;
    }

    public void setPickupPrice(double pickupPrice) {
        this.pickupPrice = pickupPrice;
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

    @Override
    public String toString() {
        return "ProductSaleDetailPojo{" +
                "productName='" + productName + '\'' +
                ", salePrice=" + salePrice +
                ", pickupPrice=" + pickupPrice +
                ", webSaleCount=" + webSaleCount +
                ", wechatSaleCount=" + wechatSaleCount +
                '}';
    }
}
