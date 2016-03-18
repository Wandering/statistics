package cn.thinkjoy.agents.service.ex.common;

/**
 * Created by admin on 2016/3/16.
 */
public interface AgentsConstant {
    /**
     * 代理商等级
     */
    public static int RANKONE=1;
    public static int RANKTWO=2;
    public static int RANKTHREE=4;
    public static int RANKERROR=-1;

    /**
     * 区域等级
     */
    public static String AREAONE="1";
    public static String AREATWO="2";
    public static String AREATHREE="4";
    public static String AREAERROR="-1";

    /**
     * 出库状态
     */
    public static String OUPUT="已出库";
    public static String NOTOUPUT="未出库";


    /**
     * 默认排序通过id排序
     */
    public static String DEFAULTORDERBY="id";
}
