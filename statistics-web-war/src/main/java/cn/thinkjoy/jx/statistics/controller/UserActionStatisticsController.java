package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IUserDayActionService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by chenmeng on 16/3/1
 *
 *登录统计
 *
 */
@Controller
@RequestMapping("/statistics/userAction")
public class UserActionStatisticsController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserActionStatisticsController.class);





    @Autowired
    private IUserDayActionService userDayActionService;


    /**
     * 按日统计找家教的PV和UV折线图。
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryFindTutorActionNum",method = RequestMethod.GET)
    public List<StatisticsPojo> queryFindTutorActionNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        String dateStart=request.getParameter("dateStart");//"2016-02-15";//
        String dateEnd=request.getParameter("dateEnd");//"2016-02-22";//
        if(StringUtils.isBlank(dateStart) && StringUtils.isBlank(dateEnd)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();

        dataMap.put("dateStart",dateStart);
        dataMap.put("dateEnd", dateEnd);
        dataMap.put("actionType", "2");
        List<StatisticsPojo> statisticsPojoList = userDayActionService.queryByTime(dataMap);
        if(statisticsPojoList!=null && statisticsPojoList.size()>0){
            return  statisticsPojoList;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 按日统计在线专家的PV和UV折线图。
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryOnlineExpertActionNum",method = RequestMethod.GET)
    public List<StatisticsPojo> queryOnlineExpertActionNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        String dateStart=request.getParameter("dateStart");//"2016-02-15";//
        String dateEnd=request.getParameter("dateEnd");//"2016-02-22";//
        if(StringUtils.isBlank(dateStart) && StringUtils.isBlank(dateEnd)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();

        dataMap.put("dateStart",dateStart);
        dataMap.put("dateEnd", dateEnd);
        dataMap.put("actionType", "3");
        List<StatisticsPojo> statisticsPojoList = userDayActionService.queryByTime(dataMap);
        if(statisticsPojoList!=null && statisticsPojoList.size()>0){
            return  statisticsPojoList;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }
    /**
     * 按日统计名师面对面的PV和UV折线图。
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryTeacherFaceToFaceActionNum",method = RequestMethod.GET)
    public List<StatisticsPojo> queryTeacherFaceToFaceActionNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        String dateStart=request.getParameter("dateStart");//"2016-02-15";//
        String dateEnd=request.getParameter("dateEnd");//"2016-02-22";//
        if(StringUtils.isBlank(dateStart) && StringUtils.isBlank(dateEnd)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();

        dataMap.put("dateStart",dateStart);
        dataMap.put("dateEnd", dateEnd);
        dataMap.put("actionType", "1");
        List<StatisticsPojo> statisticsPojoList = userDayActionService.queryByTime(dataMap);
        if(statisticsPojoList!=null && statisticsPojoList.size()>0){
            return  statisticsPojoList;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }
}
