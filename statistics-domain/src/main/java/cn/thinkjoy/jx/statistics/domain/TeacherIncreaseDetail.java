package cn.thinkjoy.jx.statistics.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by zl on 2015/12/16.
 */
public class TeacherIncreaseDetail extends BaseDomain {
    //登录号码
    private String loginNumber;
    //创建时间
    private String createDate;
    //老师姓名
    private String teacherName;
    private Long teacherId;
    //区域名称
    private String areaName;
    private Long areaId;
    //学校名称
    private String schoolName;
    private Long schoolId;
    //班级名称
    private String className;
    private Long classId;
    //课程名称
    private String courceName;

    public String getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(String loginNumber) {
        this.loginNumber = loginNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getCourceName() {
        return courceName;
    }

    public void setCourceName(String courceName) {
        this.courceName = courceName;
    }
}
