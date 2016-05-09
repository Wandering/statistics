package cn.thinkjoy.jx.statistics.controller.agents;

import cn.thinkjoy.agents.service.ex.IMonitorExService;
import cn.thinkjoy.agents.service.ex.common.AgentsInfoUtils;
import cn.thinkjoy.agents.service.ex.common.CacheService;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.jx.statistics.controller.agents.common.BaseCommonController;
import cn.thinkjoy.jx.statistics.domain.pojo.MonitorPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yhwang on 15/9/2.
 */
@Controller
@RequestMapping("/admin")
public class MonitorController extends BaseCommonController<IMonitorExService>{

    private static Logger LOGGER = LoggerFactory.getLogger(MonitorController.class);
    @Autowired
    private IMonitorExService monitorExService;
    @Autowired
    private CacheService cacheService;

    @ResponseBody
    @RequestMapping(value = "/monitors")
    public Object queryPage(@RequestParam(required = false)String queryParam,
                            @RequestParam(required = false)String area,
                            @RequestParam(required =false)Integer status,
                            @RequestParam(required =false)String startDate,
                            @RequestParam(required =false)String endDate,
                            @RequestParam(required =false)String activityStatus,
                            @RequestParam(required=false,defaultValue = "1",value = "currentPageNo") Integer page,
                            @RequestParam(required=false,defaultValue = "10",value = "pageSize") Integer rows){
        Map<String,Object> condition=new HashMap<>();
        if(StringUtils.isNotEmpty(queryParam)){
            condition.put("queryParam",queryParam);
        }
        if(StringUtils.isNotEmpty(area) && (!"00".equals(area))){
                //用户地址处理逻辑
            condition.put("area",area);
            AgentsInfoUtils.getVIPUserAreaLine(condition);
        }
        if(status!=null) {
            //异常状态0/null正常1异常
            if(status==1) {
                condition.put("errorStatus", status);
            }
            if(status==0){
                condition.put("errorStatus2", status);
            }
        }
        if(StringUtils.isNotEmpty(startDate)){
            if(StringUtils.isEmpty(endDate)){
                throw new BizException("error","截止时间不能为空");
            }
            try {
//                condition.put("activeDateStart", DateUtils.parseDateFromHeader(startDate).getTime());
//                condition.put("activeDateEnd", DateUtils.parseDateFromHeader(endDate).getTime());
                DateFormat dateFormat=new SimpleDateFormat("yy-MM-dd");
                condition.put("activeDateStart", dateFormat.parse(startDate).getTime());
                condition.put("activeDateEnd", dateFormat.parse(endDate).getTime());
            } catch (ParseException e) {
                throw new BizException("error","不是标准的时间格式");
            }

        }
        if(StringUtils.isNotEmpty(activityStatus)){
            Integer activityStatusInt=Integer.parseInt(activityStatus);
           switch (activityStatusInt){
               case 0:
                   condition.put("status2", 0);
                   break;
               case 1:
                   condition.put("status", 1);
                   break;
               default:
                   break;
           }
        }
        return doPage(page,rows,condition);
    }

    @ResponseBody
    @RequestMapping(value = "/errorChart")
    public List<MonitorPojo> errorChart(HttpServletRequest request,@RequestParam("dateStart") String startDate,@RequestParam("dateEnd") String endDate){
        if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
            throw new BizException("error","查询时间不能为空");
        }
        Cookie[] cookies=request.getCookies();
        String token = "";
        for (Cookie cookie:cookies){
            if("bizData".equals(cookie.getName()))
            {
                token = cookie.getValue();
            }
        }
        String oldUserInfo = cacheService.getValue(token);
        UserPojo userPojo;
        try {
            userPojo= JsonMapper.buildNormalMapper().fromJson(oldUserInfo, UserPojo.class);
        }catch (Exception e){
            LOGGER.error(cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getCode(), cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        String areaCode=userPojo.getAreaCode();
        Map<String,Object> condition=new HashMap<>();
        if (!areaCode.equals("00")) {
            condition.put("areaCode", areaCode);
        }
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            condition.put("startDate", dateFormat.parse(startDate+" 00:00:00").getTime());
            condition.put("endDate", dateFormat.parse(endDate+" 23:59:59").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        AgentsInfoUtils.getVIPUserAreaLine(condition);
        return monitorExService.errorChart(condition);

    }

    @ResponseBody
    @RequestMapping(value = "/findCard")
    public Object findCard(HttpServletRequest request,
                           @RequestParam(value = "errorDate") String errorDate,
                           @RequestParam(value = "errorStatus")String errorStatus,
                           @RequestParam(required=false,defaultValue = "1",value = "currentPageNo") Integer page,
                           @RequestParam(required=false,defaultValue = "10",value = "pageSize") Integer rows){
        if(StringUtils.isEmpty(errorDate)){
            throw new BizException("error","查询时间不能为空");
        }
        if(StringUtils.isEmpty(errorStatus)){
            throw new BizException("error","查询类型不能为空");
        }
        Cookie[] cookies=request.getCookies();
        String token = "";
        for (Cookie cookie:cookies){
            if("bizData".equals(cookie.getName()))
            {
                token = cookie.getValue();
            }
        }
        String oldUserInfo = cacheService.getValue(token);
        UserPojo userPojo;
        try {
            userPojo= JsonMapper.buildNormalMapper().fromJson(oldUserInfo, UserPojo.class);
        }catch (Exception e){
            LOGGER.error(cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getCode(), cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        String areaCode=userPojo.getAreaCode();
        Map<String,Object> condition=new HashMap<>();
        if (!areaCode.equals("00")) {
            condition.put("areaCode", areaCode);
        }
        condition.put("errorStatus",errorStatus);
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            condition.put("activeDateStart", dateFormat.parse(errorDate+" 00:00:00").getTime());
            condition.put("activeDateEnd", dateFormat.parse(errorDate+" 23:59:59").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return doPage(page,rows,condition);
    }

    @Override
    protected IMonitorExService getService() {
        return monitorExService;
    }
}
