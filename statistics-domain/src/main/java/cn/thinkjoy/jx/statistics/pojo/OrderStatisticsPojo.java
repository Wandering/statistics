package cn.thinkjoy.jx.statistics.pojo;

import java.io.Serializable;
import java.util.List;

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

    /**
     * 产品销售详情
     */
    private List<ProductSaleDetailPojo> productSales;

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

    public List<ProductSaleDetailPojo> getProductSales() {
        return productSales;
    }

    public void setProductSales(List<ProductSaleDetailPojo> productSales) {
        this.productSales = productSales;
    }

    @Override
    public String toString() {
        return "OrderStatisticsPojo{" +
                "departmentName='" + departmentName + '\'' +
                ", departmentCode=" + departmentCode +
                ", departmentLevel='" + departmentLevel + '\'' +
                ", netIncome=" + netIncome +
                ", notSettled=" + notSettled +
                ", settled=" + settled +
                ", productSales=" + productSales +
                '}';
    }
}
