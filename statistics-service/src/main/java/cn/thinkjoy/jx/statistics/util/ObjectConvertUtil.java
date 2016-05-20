package cn.thinkjoy.jx.statistics.util;

import cn.thinkjoy.jx.statistics.pojo.OrderStatisticsPojo;
import cn.thinkjoy.zgk.zgksystem.domain.Department;

/**
 * Created by yangguorong on 16/4/19.
 *
 * 对象转换
 */
public class ObjectConvertUtil {

    /**
     *  Department -->  OrderStatisticsPojo
     *
     * @param department
     * @return
     */
    public static OrderStatisticsPojo convertToOrderStatisticsPojo(Department department){
        OrderStatisticsPojo pojo = new OrderStatisticsPojo();
        pojo.setDepartmentCode(department.getDepartmentCode());
        pojo.setDepartmentLevel(getDepartmentLevel(department.getRoleType()));
        pojo.setDepartmentName(department.getDepartmentName());
        return pojo;
    }

    /**
     * 根据身份类型获取代理商等级
     *
     * @param roleType
     * @return
     */
    private static String getDepartmentLevel(int roleType){
        switch (roleType){
            case 1:
                return Constants.SUPER_MANAGE;
            case 2:
                return Constants.PROVICE_AGENT;
            case 3:
                return Constants.CITY_AGENT;
            case 4:
                return Constants.COUNTY_AGENT;
        }
        return Constants.SUPER_MANAGE;
    }
}
