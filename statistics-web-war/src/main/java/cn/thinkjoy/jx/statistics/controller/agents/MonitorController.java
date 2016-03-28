package cn.thinkjoy.jx.statistics.controller.agents;

import cn.thinkjoy.agents.service.ex.IMonitorExService;
import cn.thinkjoy.agents.service.ex.common.AgentsInfoUtils;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.jx.statistics.controller.agents.common.BaseCommonController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhwang on 15/9/2.
 */
@Controller
@RequestMapping("/admin")
public class MonitorController extends BaseCommonController<IMonitorExService>{

    @Autowired
    private IMonitorExService monitorExService;



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
            condition.put("errorStatus", status);
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
                   condition.put("status", 0);
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

    @Override
    protected IMonitorExService getService() {
        return monitorExService;
    }
}
