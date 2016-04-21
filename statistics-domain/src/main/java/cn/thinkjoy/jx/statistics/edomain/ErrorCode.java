package cn.thinkjoy.jx.statistics.edomain;

/**
 * Created by yangguorong on 16/4/19.
 */
public enum ErrorCode {

    USER_NOT_EXIT("0100001","用户未找到"),

    USER_EXPRIED_RELOGIN("0100008","用户信息已过期，请重新登录"),

    NO_PERMISSION("0100014","对不起,您无权限查看此内容");

    /** The code. */
    private final String code;

    /** The message. */
    private final String message;

    /**
     * Instantiates a new error type.
     *
     * @param code
     *            the code
     * @param message
     *            the message
     */
    private ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
