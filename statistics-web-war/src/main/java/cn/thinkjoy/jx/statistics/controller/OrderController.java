package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.domain.SearchField;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.utils.ObjectFactory;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.domain.Order;
import cn.thinkjoy.jx.statistics.domain.SettlementRecord;
import cn.thinkjoy.jx.statistics.edomain.ErrorCode;
import cn.thinkjoy.jx.statistics.edomain.HandleStateEnum;
import cn.thinkjoy.jx.statistics.pojo.IncomeStatisticsPojo;
import cn.thinkjoy.jx.statistics.pojo.OrderDetailPojo;
import cn.thinkjoy.jx.statistics.pojo.OrderStatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IOrderService;
import cn.thinkjoy.jx.statistics.service.ISettlementRecordService;
import cn.thinkjoy.jx.statistics.service.ex.IEXOrderService;
import cn.thinkjoy.jx.statistics.util.ModelUtil;
import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.edomain.UserRoleEnum;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/4/15.
 */
@Controller
@RequestMapping("/statistics/order")
public class OrderController {

    @Autowired
    private IEXOrderService exOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISettlementRecordService settlementRecordService;
    @Autowired
    private DeparmentApiService deparmentApiService;

    @ResponseBody
    @ApiDesc(value = "根据条件查询订单信息",owner = "杨国荣")
    @RequestMapping(value = "queryOrderPageByConditions",method = RequestMethod.GET)
    public Map<String,Object> queryOrderPageByConditions(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo == null){
            ModelUtil.throwException(ErrorCode.USER_EXPRIED_RELOGIN);
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        int orderFrom = Integer.parseInt(request.getParameter("orderFrom"));
        int handleState = Integer.parseInt(request.getParameter("handleState"));
        String orderNoOrPhone = request.getParameter("orderNoOrPhone");

        Map<String,Object> returnMap = exOrderService.queryOrderPageByConditions(
                orderFrom,
                handleState,
                orderNoOrPhone,
                userPojo.getDepartmentCode(),
                currentPageNo,
                pageSize);

        return returnMap;
    }

    @ResponseBody
    @ApiDesc(value = "修改发货状态",owner = "杨国荣")
    @RequestMapping(value = "updateSendGoodsState",method = RequestMethod.POST)
    public Object updateSendGoodsState(HttpServletRequest request) {
        String orderNo = request.getParameter("orderNo");
        Order order = (Order) orderService.findOne("order_no",orderNo);
        order.setHandleState(HandleStateEnum.HAS_SEND.getCode());
        order.setUpdateDate(System.currentTimeMillis());
        orderService.update(order);
        return ObjectFactory.getSingle();
    }

    @ResponseBody
    @ApiDesc(value = "单个部门收益总览",owner = "杨国荣")
    @RequestMapping(value = "querySingleDepartmentIncome",method = RequestMethod.GET)
    public OrderStatisticsPojo querySingleDepartmentIncome(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo == null){
            ModelUtil.throwException(ErrorCode.USER_EXPRIED_RELOGIN);
        }
        Department department = deparmentApiService.quertDepartmentInfoByCode(userPojo.getDepartmentCode());
        OrderStatisticsPojo pojo = exOrderService.querySingleDepartmentIncome(department);
        return pojo;
    }

    @ResponseBody
    @ApiDesc(value = "根据区域编号查询所有部门收益总览",owner = "杨国荣")
    @RequestMapping(value = "queryAllDepartmentIncome",method = RequestMethod.GET)
    public Page<OrderStatisticsPojo> queryAllDepartmentIncome(HttpServletRequest request) {

        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo == null){
            ModelUtil.throwException(ErrorCode.USER_EXPRIED_RELOGIN);
        }

        if(userPojo.getRoleType() != UserRoleEnum.SUPER_MANAGE.getValue()){
            ModelUtil.throwException(ErrorCode.NO_PERMISSION);
        }

        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        int areaType = Integer.parseInt(request.getParameter("areaType"));
        String areaCode = request.getParameter("areaCode");

        Page<OrderStatisticsPojo> statisticsPojoPage =  exOrderService.queryAllDepartmentIncome(
                areaCode,
                areaType,
                currentPageNo,
                pageSize);

        return statisticsPojoPage;
    }

    @ResponseBody
    @ApiDesc(value = "根据部门编号或用户ID结算",owner = "杨国荣")
    @RequestMapping(value = "settlementByDepartmentCode",method = RequestMethod.POST)
    public Object settlementByDepartmentCode(HttpServletRequest request) {

        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo == null){
            ModelUtil.throwException(ErrorCode.USER_EXPRIED_RELOGIN);
        }

        long departmentCode = Long.valueOf(request.getParameter("departmentCode"));
        double money = Double.valueOf(request.getParameter("money"));
        int type = Integer.parseInt(request.getParameter("type"));

        // TODO 需要验证输入金额是否合法
        SettlementRecord record = new SettlementRecord();
        record.setDepartmentCode(departmentCode);
        record.setMoney(money);
        record.setOperationUserId(userPojo.getAccountId());
        record.setRequestTime(System.currentTimeMillis());
        record.setType(type);
        settlementRecordService.insert(record);

        return ObjectFactory.getSingle();
    }

    @ResponseBody
    @ApiDesc(value = "根据部门编号获取结算记录",owner = "杨国荣")
    @RequestMapping(value = "getSettlementRecordsByDepartmentCode",method = RequestMethod.GET)
    public Page<SettlementRecord> getSettlementRecordsByDepartmentCode(HttpServletRequest request) {

        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        long departmentCode = Long.valueOf(request.getParameter("departmentCode"));

        Map<String, Object> condition = new HashMap<>();
        condition.put("groupOp", "and");
        condition.put("sendUserId", new SearchField("departmentCode", "=", departmentCode));

        List<SettlementRecord> records = settlementRecordService.queryPage(
                condition,
                currentPageNo*pageSize,
                pageSize,
                "requestTime",
                SqlOrderEnum.DESC);

        Integer recordsCount = settlementRecordService.count(condition);

        Page<SettlementRecord> recordPage = new Page<>();
        recordPage.setList(records);
        recordPage.setCount(recordsCount==null?0:recordsCount);
        return recordPage;
    }

    @ResponseBody
    @ApiDesc(value = "根据区域编号查询普通用户收益总览",owner = "杨国荣")
    @RequestMapping(value = "queryAllUserIncome",method = RequestMethod.GET)
    public Page<IncomeStatisticsPojo> queryAllUserIncome(HttpServletRequest request) {

        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo == null){
            ModelUtil.throwException(ErrorCode.USER_EXPRIED_RELOGIN);
        }

        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        int areaType = Integer.parseInt(request.getParameter("areaType"));
        String areaCode = request.getParameter("areaCode");
        String account = request.getParameter("account");

        Page<IncomeStatisticsPojo> pojoPage = exOrderService.queryUserIncomeByAreaCodeAndAccount(
                areaCode,
                areaType,
                account,
                currentPageNo,
                pageSize);

        return pojoPage;
    }

    @ResponseBody
    @ApiDesc(value = "根据用户ID查询用户收益详情",owner = "杨国荣")
    @RequestMapping(value = "queryUserIncomeDetailByUserId",method = RequestMethod.GET)
    public Page<OrderDetailPojo> queryUserIncomeDetailByUserId(HttpServletRequest request) {

        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        long userId = Long.valueOf(request.getParameter("userId"));

        Page<OrderDetailPojo> pojoPage = exOrderService.queryUserIncomeDetailByUserId(
                userId,
                currentPageNo,
                pageSize);

        return pojoPage;
    }
}
