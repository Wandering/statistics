package cn.thinkjoy.jx.statistics.domain;

import cn.thinkjoy.common.domain.BaseDomain;

import java.util.Date;

/**
 * Created by wangyongqiang on 15/9/18.
 * 欠费详情信息表
 *
 */
public class BillingInfo extends BaseDomain {

    private Date time;  //时间
    private Long userId; //用户Id
    private String userName; //用户名
    private String studentName;//学生姓名
    private String phoneNum;//电话
    private String eMoney;//欠费金额
    private String eAge;//欠费账龄
    private String  eLevel;//欠费等级
    private Long classId;//班级Id
    private String className;//班级名称
    private Long schoolId;//学校Id
    private String schoolName;//学校名称
    private Integer isNew;//是否最新据，0为旧数据，1为新数据

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String geteMoney() {
        return eMoney;
    }

    public void seteMoney(String eMoney) {
        this.eMoney = eMoney;
    }

    public String geteAge() {
        return eAge;
    }

    public void seteAge(String eAge) {
        this.eAge = eAge;
    }

    public String geteLevel() {
        return eLevel;
    }

    public void seteLevel(String eLevel) {
        this.eLevel = eLevel;
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

    public Integer getIsNew() { return isNew; }

    public void setIsNew(Integer isNew) { this.isNew = isNew; }
}

