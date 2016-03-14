package cn.thinkjoy.jx.statistics.domain;

/**
 * Created by Mike on 2015/12/18.
 */
public class PackageInfo {
    private String packageName;
    private String chargeType;
    private String chargeLabel;
    private int labelPriority;
    private int status;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getChargeLabel() {
        return chargeLabel;
    }

    public void setChargeLabel(String chargeLabel) {
        this.chargeLabel = chargeLabel;
    }

    public int getLabelPriority() {
        return labelPriority;
    }

    public void setLabelPriority(int labelPriority) {
        this.labelPriority = labelPriority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
