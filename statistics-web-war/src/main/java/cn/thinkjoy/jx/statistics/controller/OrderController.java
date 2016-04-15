package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.domain.pojo.OrderStatisticsPojo;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by yangguorong on 16/4/15.
 */
@Controller
@RequestMapping("/statistics/order")
public class OrderController {

    @ResponseBody
    @ApiDesc(value = "根据条件查询订单信息",owner = "杨国荣")
    @RequestMapping(value = "queryOrderListByConditions",method = RequestMethod.GET)
    public Map<String,Object> queryOrderListByConditions(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay=request.getParameter("dateDay");
        String schoolId=request.getParameter("schoolId");

        Map<String,Object> returnMap = Maps.newHashMap();

        return returnMap;
    }


    @ResponseBody
    @ApiDesc(value = "单个部门收益总览",owner = "杨国荣")
    @RequestMapping(value = "querySingleDepartmentIncome",method = RequestMethod.GET)
    public OrderStatisticsPojo queryDepartmentIncome(HttpServletRequest request) {
        OrderStatisticsPojo pojo = new OrderStatisticsPojo();
        return pojo;
    }


    @ResponseBody
    @ApiDesc(value = "所有部门收益总览",owner = "杨国荣")
    @RequestMapping(value = "queryAllDepartmentIncome",method = RequestMethod.GET)
    public Page<OrderStatisticsPojo> queryAllDepartmentIncome(HttpServletRequest request) {
        OrderStatisticsPojo pojo = new OrderStatisticsPojo();
        return pojo;
    }
}
