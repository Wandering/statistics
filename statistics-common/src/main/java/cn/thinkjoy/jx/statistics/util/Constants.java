package cn.thinkjoy.jx.statistics.util;

/**
 * Created by wangyongqiang on 15/9/7.
 */
public class Constants {

    public static final Integer DELETEED_STATUS=-1;//删除状态

    public static final Integer NORMAL_STATUS=0;//正常默认状态

    public static final Integer FATHER_MENU_STATUS=-1;//父菜单状态

    public static final Integer USER_TYPE_NORMAL = 1;//普通用户

    public static final Integer MANAGER_CODE =-1;//系统管理员、产品管理员、代理公司管理员等的部门Code和岗位Code 特殊处理为-1

    public static final Long SYSTEM_CODE =1L;//系统与岗位的关系表中的特殊处理系统Code 1

    public static final int BETWEEN_DATEDAY_QUERY_AREA_NUM=1;
    public static final int WHERR_AREAID_QUERY_SCHOOL_NUM=2;
    public static final int WHERR_SCHOOLID_QUERY_CLASS_NUM=3;

    public static final String SUPER_MANAGE = "系统管理员";
    public static final String PROVICE_AGENT = "省代";
    public static final String CITY_AGENT = "市代";
    public static final String COUNTY_AGENT = "县代";

    public static final int WECHAT = 0; // 微信来源
    public static final int WEB = 1; // web来源



}
