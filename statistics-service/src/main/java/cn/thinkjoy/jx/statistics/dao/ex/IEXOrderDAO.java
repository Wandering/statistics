package cn.thinkjoy.jx.statistics.dao.ex;

import cn.thinkjoy.jx.statistics.pojo.IncomeStatisticsPojo;
import cn.thinkjoy.jx.statistics.pojo.OrderDetailPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangguorong on 16/4/18.
 */
public interface IEXOrderDAO {

    /**
     * 根据条件查询订单数据集合
     *
     * @param orderFrom 订单来源  0：微信  1：web  -1：全部
     * @param handleState 发货状态  0：未发货  1：已发货  -1：全部
     * @param orderNoOrPhone 订单编号or联系电话
     * @Param departmentCode
     * @param index
     * @param pageSize
     * @return
     */
    List<OrderDetailPojo> queryOrderListByConditions(@Param("orderFrom") int orderFrom,
                                                     @Param("handleState") int handleState,
                                                     @Param("orderNoOrPhone") String orderNoOrPhone,
                                                     @Param("departmentCode") long departmentCode,
                                                     @Param("index") int index,
                                                     @Param("pageSize") int pageSize,
                                                     @Param("startDate") long startDate,
                                                     @Param("endDate") long endDate,
                                                     @Param("productType") int productType);

    /**
     * 根据条件查询订单数量
     *
     * @param orderFrom
     * @param handleState
     * @param orderNoOrPhone
     * @Param departmentCode
     * @return
     */
    Integer getOrderCountByConditions(@Param("orderFrom") int orderFrom,
                                      @Param("handleState") int handleState,
                                      @Param("orderNoOrPhone") String orderNoOrPhone,
                                      @Param("departmentCode") long departmentCode,
                                      @Param("startDate") long startDate,
                                      @Param("endDate") long endDate,
                                      @Param("productType") int productType);

    /**
     * 根据部门编号查询未发货的订单个数
     *
     * @param departmentCode
     * @return
     */
    Integer getNotSendCountByDepartCode(@Param("departmentCode") long departmentCode);

    /**
     * 根据部门编号和订单来源查询货物销售个数
     *
     * @param departmentCode
     * @param channel 订单来源 0:微信  1:web
     * @param productType
     * @return
     */
    Integer getGoodsCountByChannelAndDepartCode(@Param("departmentCode") long departmentCode,
                                                @Param("channel") int channel,
                                                @Param("productType") int productType);

    /**
     * 根据部门编号 or 用户ID 获取已结算的金额
     *
     * @param departmentCode
     * @param type
     * @return
     */
    Double getSettledByDepartCode(@Param("departmentCode") long departmentCode,
                                  @Param("type") int type);

    /**
     * 根据地区查询所有用户总收益
     *
     * @param areaCode
     * @param areaType 类型  -1：所有地区 1:省 2：市  3：区县
     * @param account
     * @param index
     * @param pageSize
     * @return
     */
    List<IncomeStatisticsPojo> queryUserIncomeByAreaCodeAndAccount(@Param("areaCode") String areaCode,
                                                                   @Param("areaType") int areaType,
                                                                   @Param("account") String account,
                                                                   @Param("index") int index,
                                                                   @Param("pageSize") int pageSize);

    /**
     * 根据地区查询所有用户总收益的个数
     *
     * @param areaCode
     * @param areaType
     * @param account
     * @return
     */
    Integer getCountByAreaCodeAndAccount(@Param("areaCode") String areaCode,
                                           @Param("areaType") int areaType,
                                           @Param("account") String account);

    /**
     * 根据用户ID查询用户收益详情
     *
     * @param userId
     * @param index
     * @param pageSize
     * @return
     */
    List<OrderDetailPojo> queryUserIncomeDetailByUserId(@Param("userId") long userId,
                                                        @Param("index") int index,
                                                        @Param("pageSize") int pageSize);

    /**
     * 根据用户ID获取用户收益详情总数
     *
     * @param userId
     * @return
     */
    Integer getUserIncomeDetailCountByUserId(@Param("userId") long userId);

    /**
     * 根据用户ID和类型获取用户总收益
     *
     * @param userId
     * @param type 0:代理商  1:普通用户
     * @return
     */
    Double getAllIncomeByUserIdAndType(@Param("userId") long userId,
                                       @Param("type") long type);
}
