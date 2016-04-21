package cn.thinkjoy.jx.statistics.pojo;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by Mike on 2016/1/16.
 *
 * 活跃统计
 */
public class UserActiveInfo extends BaseDomain {

    private String classfyName;//公众号类型
    private String userName;//用户名
    private long articleNum;//发布文章数
    private long activityNum;//发布活动数
    private int activeDayNum;//活跃天数

    public String getClassfyName() {
        return classfyName;
    }

    public void setClassfyName(String classfyName) {
        this.classfyName = classfyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(long articleNum) {
        this.articleNum = articleNum;
    }

    public long getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(long activityNum) {
        this.activityNum = activityNum;
    }

    public int getActiveDayNum() {
        return activeDayNum;
    }

    public void setActiveDayNum(int activeDayNum) {
        this.activeDayNum = activeDayNum;
    }

}
