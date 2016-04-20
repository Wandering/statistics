package cn.thinkjoy.jx.statistics.edomain;

/**
 * Created by yangguorong on 16/4/19.
 *
 * 发货状态枚举  0：未发货  1：已发货
 */
public enum HandleStateEnum {

    HAS_SEND(1),

    NO_SEND(0);

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    HandleStateEnum(int code) {
        this.code = code;
    }
}
