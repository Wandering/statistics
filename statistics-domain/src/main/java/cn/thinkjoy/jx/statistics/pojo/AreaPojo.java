package cn.thinkjoy.jx.statistics.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangguorong on 16/4/26.
 */
public class AreaPojo implements Serializable{

    /**
     * 区域ID
     */
    private long areaId;

    /**
     * 区域名
     */
    private String areaName;

    /**
     * 父ID
     */
    private long parentId;

    /**
     * 类型 1:省  2:市  3:区
     */
    private int type;

    /**
     * 区域子集
     */
    private List<AreaPojo> childList;

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<AreaPojo> getChildList() {
        return childList;
    }

    public void setChildList(List<AreaPojo> childList) {
        this.childList = childList;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
