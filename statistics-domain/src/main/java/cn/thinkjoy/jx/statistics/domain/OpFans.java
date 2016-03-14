package cn.thinkjoy.jx.statistics.domain;

import cn.thinkjoy.common.domain.BaseDomain;

import java.util.Date;

/**
 * Created by wangyongqiang on 15/9/18.
 * 服务账户日粉丝数统计
 *
 */
public class OpFans extends BaseDomain {

    private Long userId; //用户Id
    private String userName; //用户名
    private String dayTime;
    private Integer num;

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

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}

