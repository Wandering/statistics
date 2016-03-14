package cn.thinkjoy.jx.statistics.controller;

/**
 * Created by wpliu on 15/10/13.
 */

import cn.thinkjoy.jx.statistics.domain.OpAccount;
import cn.thinkjoy.jx.statistics.domain.OpAccountClassfy;
import cn.thinkjoy.jx.statistics.service.IOpStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("/statistics/op")
public class OpStatisticsController {

    private static Logger LOGGER = LoggerFactory.getLogger(OpStatisticsController.class);

    @Autowired
    private IOpStatisticsService iOpStatisticsService;

   @ResponseBody
   @RequestMapping(value = "getClassfyInfo",method = RequestMethod.GET)
   public List<OpAccountClassfy> getClassfyInfo(HttpServletRequest request){
       List<OpAccountClassfy> opAccountClassfies=new ArrayList<>();
       List<Map<String,Object>>  classfyMap= iOpStatisticsService.getClassfyInfo();
       for(Map<String,Object> map:classfyMap){
           OpAccountClassfy opAccountClassfy=new OpAccountClassfy();
           Long classfyId=Long.valueOf(map.get("id").toString());
           opAccountClassfy.setId(classfyId);
           opAccountClassfy.setName(map.get("name").toString());
           List<OpAccount>  opAccounts=iOpStatisticsService.getAccountInfoByClassfyId(classfyId);
           opAccountClassfy.setChildren(opAccounts);
           opAccountClassfies.add(opAccountClassfy);
       }
       return opAccountClassfies;
   }


    @ResponseBody
    @RequestMapping(value = "getAccountFans",method = RequestMethod.GET)
    public List<Map<String,Object>> getAccountFans(HttpServletRequest request){
        List<Map<String,Object>> map=new ArrayList<>();
        Long userId= Long.valueOf(request.getParameter("opAccountId"));
        String dateStart= request.getParameter("dateStart");
        String dateEnd=request.getParameter("dateEnd");
        map=iOpStatisticsService.getAccountFans(userId,dateStart,dateEnd);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "getAccountArticles",method = RequestMethod.GET)
    public List<Map<String,Object>> getAccountArticles(HttpServletRequest request){
        List<Map<String,Object>> map=new ArrayList<>();
        Long userId= Long.valueOf(request.getParameter("opAccountId"));
        String dateStart= request.getParameter("dateStart");
        String dateEnd=request.getParameter("dateEnd");
        map=iOpStatisticsService.getAccountArticles(userId,dateStart,dateEnd);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = "getAccountEvents",method = RequestMethod.GET)
    public List<Map<String,Object>> getAccountEvents(HttpServletRequest request){
        List<Map<String,Object>> map=new ArrayList<>();
//        Long userId= Long.valueOf(request.getParameter("opAccountId"));
//        String dateStart= request.getParameter("dateStart");
//        String dateEnd=request.getParameter("dateEnd");
//        map=iOpStatisticsService.getAccountEvents(userId,dateStart,dateEnd);
        return map;
    }


}
