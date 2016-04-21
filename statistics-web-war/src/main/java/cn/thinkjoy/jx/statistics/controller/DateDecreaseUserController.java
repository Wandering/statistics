package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IDateDecreaseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Mike on 2016/1/25.
 */
@Controller
@RequestMapping("/statistics/dereaseUser")
public class DateDecreaseUserController {
    private static Logger LOGGER = LoggerFactory.getLogger(DateDecreaseUserController.class);

    @Autowired
    IDateDecreaseUserService dateDecreaseUserService;


    /**
     * 获取减少用户折线图
     * @param request
     * @return
     */
    @RequestMapping(value = "queryDecreaseUser", method = RequestMethod.GET)
    @ResponseBody
    public List<StatisticsPojo> queryDecreaseUser(HttpServletRequest request){
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        Map<String, Object> mapForQuery =  initQueryMap(request);
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        List<StatisticsPojo> list = dateDecreaseUserService.queryByTime(mapForQuery);
        if (null != list && list.size() > 0) {
            return list;
        } else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 条件查询删除用户接口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryDecreaseUserByAreaIds", method = RequestMethod.GET)
    public Page<StatisticsPojo> queryDecreaseUserByAreaIds(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        Map<String, Object> mapForQuery =  initQueryMap(request);
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        Page<StatisticsPojo> page = dateDecreaseUserService.queryByAreaIds(mapForQuery, (pageNo - 1) * pageSize, pageSize);
        return page;
    }
    /**
     * 条件查询删除用户接口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryDecreaseUserBySchoolIds", method = RequestMethod.GET)
    public Page<StatisticsPojo> queryDecreaseUserBySchoolIds(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        Map<String, Object> mapForQuery =  initQueryMap(request);
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        Page<StatisticsPojo> page = dateDecreaseUserService.queryBySchoolIds(mapForQuery, (pageNo - 1) * pageSize, pageSize);
        return page;
    }
    /**
     * 条件查询删除用户接口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryDecreaseUserByClassIds", method = RequestMethod.GET)
    public Page<StatisticsPojo> queryDecreaseUserByClassIds(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        Map<String, Object> mapForQuery =  initQueryMap(request);
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        Page<StatisticsPojo> page = dateDecreaseUserService.queryByClassIds(mapForQuery, (pageNo - 1) * pageSize, pageSize);
        return page;
    }
    public List<Long> initIdDate(String [] idArray)
    {
       List<Long> idList = new ArrayList<>();
        if(idArray!=null && idArray.length>0)
        {
            for(String tempStr:idArray)
            {
                idList.add(Long.parseLong(tempStr.trim()));
            }
        }

        return idList;
    }
    public Map<String, Object> initQueryMap(HttpServletRequest request)
    {
        Map<String, Object> mapForQuery = new HashMap<>();
        List<Long> areaIdList =null;
        List<Long> schoolIdList =null;
        List<Long> classIdList =null;
        //一下代码是测试本节口时模拟的假数据
        String areaIdStr=  request.getParameter("areaIds");
        if(areaIdStr!=null)
        {
            areaIdList = initIdDate(areaIdStr.split(","));

        }
        String schoolIdStr=  request.getParameter("schoolIds");
        if(schoolIdStr!=null)
        {
            schoolIdList = initIdDate(schoolIdStr.split(","));

        }
        String classIdStr=  request.getParameter("classIds");
        if(classIdStr!=null)
        {
            classIdList = initIdDate(classIdStr.split(","));

        }
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        String dateDay = request.getParameter("dateDay");
      /*  if (StringUtils.isBlank(dateStart) && StringUtils.isBlank(dateEnd)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null; //初始化date
        Date date2 = null; //初始化date
        Date date3 = null; //初始化date
        try{
            /*date1 = sdf.parse("2016-12-04 00:00:00"); //Mon Jan 14 00:00:00 CST 2013
            date2 = sdf.parse("2016-12-06 00:00:00"); //Mon Jan 14 00:00:00 CST 2013*/
            if(dateStart!=null)
            {
                date1 = sdf.parse(dateStart);
                java.sql.Date timestamp1 = new java.sql.Date(date1.getTime());
                mapForQuery.put("dateStart", timestamp1);
            }
            if(dateEnd!=null)
            {
                date2 = sdf.parse(dateEnd);
                java.sql.Date timestamp2 = new java.sql.Date(date2.getTime());
                mapForQuery.put("dateEnd", timestamp2);
            }
            if(dateDay!=null)
            {
                date3 = sdf.parse(dateDay);
                java.sql.Date timestamp3 = new java.sql.Date(date3.getTime());
                mapForQuery.put("dateDay", timestamp3);
            }

        }catch (Exception e)
        {
            LOGGER.debug(e.toString());
        }
        if (null == areaIdList || areaIdList.size() == 0 || (areaIdList != null && areaIdList.size() == 1 && areaIdList.get(0) == 0)) {
            mapForQuery.put("areaIds", null);
        } else {
            mapForQuery.put("areaIds", areaIdList);
        }
        if (null == schoolIdList || schoolIdList.size() == 0 || (null != schoolIdList && schoolIdList.size() == 1 && schoolIdList.get(0) == 0)) {
            mapForQuery.put("schoolIds", null);
        } else {
            mapForQuery.put("schoolIds", schoolIdList);
        }
        if (null == classIdList || classIdList.size() == 0 || (null != classIdList && classIdList.size() == 1 && classIdList.get(0) == 0)) {
            mapForQuery.put("classIds", null);
        } else {
            mapForQuery.put("classIds", classIdList);
        }
        mapForQuery.put("pageNo", pageNo);
        mapForQuery.put("pageSize", pageSize);
        return mapForQuery;
    }

}
