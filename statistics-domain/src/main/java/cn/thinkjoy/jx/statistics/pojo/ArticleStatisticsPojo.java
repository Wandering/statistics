package cn.thinkjoy.jx.statistics.pojo;

/**
 * Created by jzli on 15/10/13.
 * 文章统计
 */
public class ArticleStatisticsPojo {
    private String userName;    //公众账号
    private String subject;     //文章主题
    private Long reviewTime;    //审核时间
    private Integer readCount;  //推送人数
    private Integer hit;        //浏览量（点击量）

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Long reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getHit() {
        return hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }
}
