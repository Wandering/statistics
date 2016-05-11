package cn.thinkjoy.jx.statistics.service.ex;

import cn.thinkjoy.jx.statistics.pojo.IncomeStatisticsPojo;
import cn.thinkjoy.jx.statistics.pojo.OrderDetailPojo;
import cn.thinkjoy.jx.statistics.pojo.OrderStatisticsPojo;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.domain.Department;

import java.util.Map;

/**
 * Created by yangguorong on 16/4/18.
 */
public interface IEXOrderService {

    /**
     * 根据条件查询订单数据
     *
     * @param orderFrom 订单来源  0：微信  1：web  -1：全部
     * @param handleState 发货状态  0：未发货  1：已发货  -1：全部
     * @param orderNoOrPhone 订单编号or联系电话
     * @Param departmentCode
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    Map<String,Object> queryOrderPageByConditions(int orderFrom,int handleState,String orderNoOrPhone,long departmentCode,int currentPageNo,int pageSize);

    /**
     * 系统管理员根据地区查询所有代理商总收益
     *
     * @param areaCode
     * @param areaType
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    Page<OrderStatisticsPojo> queryAllDepartmentIncome(String areaCode, int areaType, int currentPageNo, int pageSize);

    /**
     * 根据部门编号查询单个部门的收益总览
     *
     * @param department
     * @return
     */
    OrderStatisticsPojo querySingleDepartmentIncome(Department department);

    /**
     * 根据用户ID获取用户注册地址
     *
     * @param userId
     * @return
     */
    String getUserRegistAddressByUserId(long userId);

    /**
     * 系统管理员根据地区查询所有用户总收益
     *
     * @param areaCode
     * @param areaType 类型  -1：所有地区 1:省 2：市  3：区县
     * @Param account
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    Page<IncomeStatisticsPojo> queryUserIncomeByAreaCodeAndAccount(String areaCode, int areaType,String account, int currentPageNo, int pageSize);

    /**
     * 根据用户ID获取用户收益详情
     *
     * @param userId
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    Page<OrderDetailPojo> queryUserIncomeDetailByUserId(long userId, int currentPageNo, int pageSize);

    /**
     * 检测用户后台结算金额是否合法
     *
     * @param userId
     * @param type 0:代理商 1:普通用户
     * @param money
     * @return
     */
    boolean checkMoneyIsLegal(long userId,int type,double money);
}
