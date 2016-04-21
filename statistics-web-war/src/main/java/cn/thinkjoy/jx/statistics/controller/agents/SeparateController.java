package cn.thinkjoy.jx.statistics.controller.agents;

import cn.thinkjoy.agents.service.ex.ISeparateExService;
import cn.thinkjoy.agents.service.ex.common.CacheService;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import com.google.common.collect.Maps;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zuohao on 16/4/19.
 */
@Controller
@RequestMapping("/separateController")
public class SeparateController {

    @Autowired
    private ISeparateExService separateExService;
    @Autowired
    private CacheService cacheService;

    @ResponseBody
    @RequestMapping(value = "findSeparate",method = RequestMethod.GET)
    public Map<String,Object> findSeparate(HttpServletRequest request){
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
            throw new BizException(cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getCode(), cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        String areaCode=userPojo.getAreaCode();
        if (!areaCode.equals("00")) {
            return null;
        }
        Map<String,Object> returnMap= Maps.newHashMap();
        returnMap.put("count",1);
        returnMap.put("list", separateExService.findSeparate());
        return returnMap;
    }

    @ResponseBody
    @RequestMapping(value = "updateSeparate",method = RequestMethod.GET)
    public Map<String,Object> updateSeparate(HttpServletRequest request,@RequestParam(value = "levelProfits1",required = true)Integer levelProfits1,
                               @RequestParam(value = "levelProfits2",required = true)Integer levelProfits2){
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
            throw new BizException(cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getCode(), cn.thinkjoy.zgk.zgksystem.common.ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        String areaCode=userPojo.getAreaCode();
        if (!areaCode.equals("00")) {
            return null;
        }
        separateExService.updateSeparate(levelProfits1, levelProfits2);
        Map<String,Object> returnMap= Maps.newHashMap();
        returnMap.put("count",1);
        returnMap.put("list", separateExService.findSeparate());
        return returnMap;
    }
}
