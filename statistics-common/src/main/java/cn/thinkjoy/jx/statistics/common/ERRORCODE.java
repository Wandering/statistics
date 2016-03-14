package cn.thinkjoy.jx.statistics.common;


/**
 */
public enum ERRORCODE {

	/**
	 * 参数异常
	 */
	QUERYJSON_ISNULL("0100001", "查询参数为空"),
	PARAM_ISERROR("0100002","参数错误"),
	PARAM_ISNULL("0100003","参数不能为空"),
	PARAM_ISTOLONG("0100004","参数长度超过限制"),
	OLDPASSWD_ISNULL("0100005","原密码不能为空"),
	INCONSISTENT_PASSWD("0100006","两次密码不一致，请重新输入"),
	PASSWD_FORMAT_ERROR("0100007","新密码不符合规范，请重新输入"),
	USER_EXPRIED_RELOGIN("0100008","用户信息已过期，请重新登录"),
	USER_NAME_OR_PASSWORD_DO_NOT_MATCH("0100009","旧密码错误"),
	NO_MESSAGE("0100010","没有获取到相关记录"),
	USERISDELETE("0100011","用户已经注销"),
	NO_MORE_CONTENT("0100012","没有更多消息了"),
	INSERT_ERROR("0100013","插入失败"),
	NO_PERMISSION("0100014","无权限"),
	NO_SESSION("0100015","SESSION失效"),
	JSONCONVERT_ERROR("0100016","JSON转OBJECT错误"),
	ALREADY_EXIST_ERROR("0100017","已存在的信息"),
	NOT_DELETEED("0100018","请先联系其他管理员删除从属信息"),
	DELETE_ERROR("0100019","删除失败"),
	UPDATE_ERROR("0100020","修改失败"),
	TREEBEAN_ISNULL("0100021","菜单树为空"),
	NO_MESSAGE_F("0100022","任务1所选日期无记录，请重新选择"),
	NO_MESSAGE_S("0100023","任务2所选日期无记录，请重新选择"),
	ENDDAY_LOW_STARTDAY_ERROR("0100024", "结束日期不能小于开始日期，请重新选择");

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
	private ERRORCODE(String code, String message) {
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