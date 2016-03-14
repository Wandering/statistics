package cn.thinkjoy.jx.statistics.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by wangyongqiang on 15/9/18.
 *
 * 日班级用户聊天条数汇总
 *
 */
public class UserGroupChatDetail extends BaseDomain{
    private Long userId;
    private Integer userType;
    private Long classId;//班级Id
    private String className;//班级名称
    private Long schoolId;//学校Id
    private String  schoolName;//学校名称
    private Long areaId;//区域Id
    private String  areaName;//区域名称
    private String dateDay;//时间天

    private Integer num; //数量

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public UserGroupChatDetail() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
