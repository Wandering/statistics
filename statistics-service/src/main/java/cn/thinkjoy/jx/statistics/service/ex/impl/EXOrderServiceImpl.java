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

import java.math.BigDecimal;
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

        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put("count",detailPojoCount);
        returnMap.put("list",detailPojos);
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
        long departmentId = Long.valueOf(department.getId().toString());
        // 微信销量
        Integer wechatSaleCount = exOrderDAO.getGoodsCountByChannelAndDepartCode(
                departmentCode,
                Constants.WECHAT);
        int wechatSaleCountTmp = wechatSaleCount==null?0:wechatSaleCount;
        pojo.setWechatSaleCount(wechatSaleCountTmp);

        // web销量
        Integer webSaleCount = exOrderDAO.getGoodsCountByChannelAndDepartCode(
                departmentCode,
                Constants.WEB);
        int webSaleCountTmp = webSaleCount==null?0:webSaleCount;
        pojo.setWebSaleCount(webSaleCountTmp);

        // 网上总收益
//        double netIncome =
//                wechatSaleCountTmp*department.getWechatPrice() +
//                        webSaleCountTmp*department.getWebPrice();
        Double netIncome = exOrderDAO.getAllIncomeByUserIdAndType(
                departmentId,
                0);
        // TODO 分成的收益单位为分
        netIncome = netIncome==null?0:netIncome;
        pojo.setNetIncome(netIncome/100);

        // 已结算的金额
        Double settled = exOrderDAO.getSettledByDepartCode(departmentId,0);
        settled = settled==null?0:settled;
        pojo.setSettled(settled);
        pojo.setNotSettled((netIncome-settled*100)/100);

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
            Double settled = exOrderDAO.getSettledByDepartCode(pojo.getUserId(),1);
            settled = settled==null?0:settled;
            pojo.setSettled(settled);
            // TODO 分成的收益单位为分
            pojo.setNotSettled((pojo.getAllIncome()-settled*100)/100);

            // 注册地址
            String registAddress = getUserRegistAddressByUserId(pojo.getUserId());
            pojo.setRegistAddress(registAddress);
            // TODO 分成的收益单位为分
            pojo.setAllIncome(pojo.getAllIncome()/100);
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

        // TODO 由于分成单位是分,所以此处要转换成元
        for(OrderDetailPojo pojo : pojos){
            pojo.setRewardMoney(pojo.getRewardMoney()/100);
        }

        Integer count = exOrderDAO.getUserIncomeDetailCountByUserId(userId);

        Page<OrderDetailPojo> pojoPage = new Page<>();
        pojoPage.setList(pojos);
        pojoPage.setCount(count);

        return pojoPage;
    }

    @Override
    public boolean checkMoneyIsLegal(long userId,int type,double money) {
        // 总收益
        Double netIncome = exOrderDAO.getAllIncomeByUserIdAndType(
                userId,
                type);
        if(netIncome == null || netIncome <= 0){
            return false;
        }
        // 已结算的金额
        Double settled = exOrderDAO.getSettledByDepartCode(userId,type);
        if(settled == null || settled == 0){
            return money <= netIncome/100;
        }
        return money <= (netIncome - settled*100)/100;
    }
}
