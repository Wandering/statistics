package cn.thinkjoy.jx.statistics.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangyongqiang on 15/9/18.
 * 欠费详情信息表
 *
 */
public class StatisticsPojo implements Serializable {

    private Long classId;//班级Id
    private String className;//班级名称
    private Long schoolId;//学校Id
    private String  schoolName;//学校名称
    private Long areaId;//区域Id
    private String  areaName;//区域名称
    private String dateDay;//时间天
    private Integer teacherNum;//教师数量
    private Integer parentNum;//家长数量
    private Integer delStudentNum;//删除学生数量
    private Integer graduateStudentNum;//毕业学生数量
    private Integer parentWebNum;//家长Web数量  预留
    private Integer parentAppNum;//家长App数量 预留
    private Integer teacherWebNum;//教师Web数量 预留
    private Integer teacherAppNum;//教师App数量 预留

    //20160301 chenmeng add
    private Integer pvNum;
    private Integer uvNum;

    private Long userId;
    private Long userType;

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public Integer getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(Integer teacherNum) {
        this.teacherNum = teacherNum;
    }

    public Integer getParentNum() {
        return parentNum;
    }

    public void setParentNum(Integer parentNum) {
        this.parentNum = parentNum;
    }

    public Integer getParentWebNum() {
        return parentWebNum;
    }

    public void setParentWebNum(Integer parentWebNum) {
        this.parentWebNum = parentWebNum;
    }

    public Integer getParentAppNum() {
        return parentAppNum;
    }

    public void setParentAppNum(Integer parentAppNum) {
        this.parentAppNum = parentAppNum;
    }

    public Integer getTeacherWebNum() {
        return teacherWebNum;
    }

    public void setTeacherWebNum(Integer teacherWebNum) {
        this.teacherWebNum = teacherWebNum;
    }

    public Integer getTeacherAppNum() {
        return teacherAppNum;
    }

    public void setTeacherAppNum(Integer teacherAppNum) {
        this.teacherAppNum = teacherAppNum;
    }

    public Integer getDelStudentNum() {
        return delStudentNum;
    }

    public void setDelStudentNum(Integer delStudentNum) {
        this.delStudentNum = delStudentNum;
    }

    public Integer getGraduateStudentNum() {
        return graduateStudentNum;
    }

    public void setGraduateStudentNum(Integer graduateStudentNum) {
        this.graduateStudentNum = graduateStudentNum;
    }

    public Integer getPvNum() {
        return pvNum;
    }

    public void setPvNum(Integer pvNum) {
        this.pvNum = pvNum;
    }

    public Integer getUvNum() {
        return uvNum;
    }

    public void setUvNum(Integer uvNum) {
        this.uvNum = uvNum;
    }

    @Override
    public String toString() {
        return "StatisticsPojo{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", dateDay='" + dateDay + '\'' +
                ", teacherNum=" + teacherNum +
                ", parentNum=" + parentNum +
                ", delStudentNum=" + delStudentNum +
                ", graduateStudentNum=" + graduateStudentNum +
                ", parentWebNum=" + parentWebNum +
                ", parentAppNum=" + parentAppNum +
                ", teacherWebNum=" + teacherWebNum +
                ", teacherAppNum=" + teacherAppNum +
                ", pvNum=" + pvNum +
                ", uvNum=" + uvNum +
                ", userId=" + userId +
                ", userType=" + userType +
                '}';
    }
}

