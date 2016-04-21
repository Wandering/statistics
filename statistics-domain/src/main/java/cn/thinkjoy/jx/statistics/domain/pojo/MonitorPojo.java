package cn.thinkjoy.jx.statistics.domain.pojo;

import java.io.Serializable;

/**
 * Created by zuohao on 16/4/15.
 */
public class MonitorPojo implements Serializable {
    private String date;
    private int number;
    private int status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
