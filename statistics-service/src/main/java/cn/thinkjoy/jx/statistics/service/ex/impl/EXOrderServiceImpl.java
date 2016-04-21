package cn.thinkjoy.jx.statistics.service.ex.impl;

import cn.thinkjoy.agents.service.ex.common.AreaCacheUtils;
import cn.thinkjoy.jx.statistics.dao.ex.IEXOrderDAO;
import cn.thinkjoy.jx.statistics.domain.UserInfo;
import cn.thinkjoy.jx.statistics.edomain.ErrorCode;
import cn.thinkjoy.jx.statistics.pojo.IncomeStatisticsPojo;
import cn.thinkjoy.jx.statistics.pojo.OrderDetailPojo;
import cn.thinkjoy.jx.statistics.pojo.OrderStatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IUserInfoService;
import cn.thinkjoy.jx.statistics.service.ex.IEXOrderService;
import cn.thinkjoy.jx.statistics.util.Constants;
import cn.thinkjoy.jx.statistics.util.ModelUtil;
import cn.thinkjoy.jx.statistics.util.ObjectConvertUtil;
import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/4/18.
 */
@Service("eXOrderService")
public class EXOrderServiceImpl implements IEXOrderService {

    @Autowired
    private IEXOrderDAO exOrderDAO;

    @Autowired
    private DeparmentApiService deparmentApiService;

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public Map<String, Object> queryOrderPageByConditions(int orderFrom, int handleState, String orderNoOrPhone,long departmentCode, int currentPageNo, int pageSize) {

        List<OrderDetailPojo> detailPojos = exOrderDAO.queryOrderListByConditions(
                orderFrom,
                handleState,
                orderNoOrPhone,
                departmentCode,
                (currentPageNo-1)*pageSize,
                pageSize);

        int detailPojoCount = exOrderDAO.getOrderCountByConditions(
                orderFrom,
                handleState,
                orderNoOrPhone,
                departmentCode);

        Page<OrderDetailPojo> detailPojoPage = new Page<>();
        detailPojoPage.setList(detailPojos);
        detailPojoPage.setCount(detailPojoCount);

        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put("page",detailPojoPage);
        returnMap.put("message",convertMessage(departmentCode));
        return returnMap;
    }

    /**
     * 组装消息  "未发货：共XX个订单，包含XX套盒子"
     *
     * @return
     */
    private String convertMessage(long departmentCode){
        Integer count = exOrderDAO.getNotSendCountByDepartCode(departmentCode);
        StringBuffer buffer = new StringBuffer();
        buffer.append("未发货：共");
        buffer.append(count==null?0:count);
        buffer.append("个订单，包含");
        buffer.append(count==null?0:count);
        buffer.append("套盒子");
        return buffer.toString();
    }

    @Override
    public Page<OrderStatisticsPojo> queryAllDepartmentIncome(String areaCode, int areaType, int currentPageNo, int pageSize) {

        // 根据区域编号查询区域下代理商信息
        List<Department> departments = deparmentApiService.queryDepartmentsByAreaCode(
                areaCode,
                areaType,
                currentPageNo,
                pageSize);

        // 循环统计代理商销量及收益
        List<OrderStatisticsPojo> pojos = Lists.newArrayList();
        for(Department department : departments){
            OrderStatisticsPojo pojo = querySingleDepartmentIncome(department);
            pojos.add(pojo);
        }

        // 符合条件的部门总个数
        int departmentCount = deparmentApiService.getDepartmentCountByAreaCode(
                areaCode,
                areaType);

        Page<OrderStatisticsPojo> pojoPage = new Page<>();
        pojoPage.setList(pojos);
        pojoPage.setCount(departmentCount);

        return pojoPage;
    }


    @Override
    public OrderStatisticsPojo querySingleDepartmentIncome(Department department) {

        OrderStatisticsPojo pojo = ObjectConvertUtil.convertToOrderStatisticsPojo(department);

        long departmentCode = department.getDepartmentCode();
        // 微信销量
        Integer wechatSaleCount = exOrderDAO.getGoodsCountByChannelAndDepartCode(
                departmentCode,
                Constants.WECHAT);
        int wechatSaleCountTmp = wechatSaleCount==null?0:wechatSaleCount;
        pojo.setWechatSaleCount(wechatSaleCountTmp);

        // web销量
        Integer webSaleCount = exOrderDAO.getGoodsCountByChannelAndDepartCode(
                departmentCode,
                Constants.WECHAT);
        int webSaleCountTmp = webSaleCount==null?0:webSaleCount;
        pojo.setWebSaleCount(webSaleCountTmp);

        // 网上总收益
        double netIncome =
                wechatSaleCountTmp*department.getWechatPrice() +
                        webSaleCountTmp*department.getWebPrice();
        pojo.setNetIncome(netIncome);

        // 已结算的金额
        Integer settled = exOrderDAO.getSettledByDepartCode(departmentCode);
        pojo.setSettled(settled==null?0:settled);
        pojo.setNotSettled(netIncome-pojo.getSettled());

        return pojo;
    }

    // TODO 去除缓存强依赖
    @Override
    public String getUserRegistAddressByUserId(long userId) {

        UserInfo userInfo = (UserInfo) userInfoService.findOne("id",userId);
        if(userInfo == null){
            ModelUtil.throwException(ErrorCode.USER_NOT_EXIT);
        }
        StringBuffer registAddress = new StringBuffer();
        if(!"00".equals(userInfo.getProvinceId())){
            String provice = AreaCacheUtils.getAreaCache("province",userInfo.getProvinceId());
            registAddress.append(provice);
        }
        if(!"00".equals(userInfo.getProvinceId())){
            String city = AreaCacheUtils.getAreaCache("city",userInfo.getCityId());
            registAddress.append(city);
        }
        if(!"00".equals(userInfo.getProvinceId())){
            String county = AreaCacheUtils.getAreaCache("county",userInfo.getCountyId());
            registAddress.append(county);
        }
        return registAddress.toString();
    }

    @Override
    public Page<IncomeStatisticsPojo> queryUserIncomeByAreaCodeAndAccount(String areaCode, int areaType, String account,int currentPageNo, int pageSize) {

        List<IncomeStatisticsPojo> pojos = exOrderDAO.queryUserIncomeByAreaCodeAndAccount(
                areaCode,
                areaType,
                account,
                (currentPageNo-1)*pageSize,
                pageSize);

        for(IncomeStatisticsPojo pojo : pojos){
            // 已结算的金额
            Integer settled = exOrderDAO.getSettledByDepartCode(pojo.getUserId());
            pojo.setSettled(settled==null?0:settled);
            pojo.setNotSettled(pojo.getAllIncome()-pojo.getSettled());

            // 注册地址
            String registAddress = getUserRegistAddressByUserId(pojo.getUserId());
            pojo.setRegistAddress(registAddress);
        }

        Integer count = exOrderDAO.getCountByAreaCodeAndAccount(
                areaCode,
                areaType,
                account);

        Page<IncomeStatisticsPojo> pojoPage = new Page<>();
        pojoPage.setList(pojos);
        pojoPage.setCount(count==null?0:count);

        return pojoPage;
    }

    @Override
    public Page<OrderDetailPojo> queryUserIncomeDetailByUserId(long userId, int currentPageNo, int pageSize) {

        List<OrderDetailPojo> pojos = exOrderDAO.queryUserIncomeDetailByUserId(
                userId,
                (currentPageNo-1)*pageSize,
                pageSize);

        Integer count = exOrderDAO.getUserIncomeCountByUserId(userId);

        Page<OrderDetailPojo> pojoPage = new Page<>();
        pojoPage.setList(pojos);
        pojoPage.setCount(count);

        return pojoPage;
    }
}
