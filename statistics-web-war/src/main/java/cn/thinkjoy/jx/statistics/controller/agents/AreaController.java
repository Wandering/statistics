package cn.thinkjoy.jx.statistics.controller.agents;

import cn.thinkjoy.agents.service.ex.IAreaExService;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.jx.statistics.pojo.AreaPojo;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhwang on 15/9/2.
 */
@Controller
@RequestMapping("/admin")
public class AreaController{

    @Autowired
    private IAreaExService areaExService;

    /**
     * 获取当前用户下一级区域
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCurrUserNextArea")
    public List<Map<String,Object>> getCurrUserNextArea(String nextArea){
        if(StringUtils.isEmpty(nextArea)) {
            return areaExService.getFlowNextArea();
        }else {
            return areaExService.getFlowNextArea(nextArea);
        }
    }

    @ResponseBody
    @ApiDesc(value = "获取所有区域",owner = "杨国荣")
    @RequestMapping(value = "/getAllAreaInfo",method = RequestMethod.GET)
    public List<AreaPojo> getAllAreaInfo(){

        List<AreaPojo> provinceList = areaExService.getAllProvince();
        List<AreaPojo> cityList = areaExService.getAllCity();
        List<AreaPojo> countyList = areaExService.getAllCounty();

        // 组装市区
        for(AreaPojo cityPojo : cityList){
            List<AreaPojo> childCountyList = Lists.newArrayList();
            for(AreaPojo countyPojo : countyList){
                if(cityPojo.getAreaId() == countyPojo.getParentId()){
                    countyPojo.setType(3);
                    childCountyList.add(countyPojo);
                }
            }
            cityPojo.setChildList(childCountyList);
        }

        // 组装省市
        for(AreaPojo provincePojo : provinceList){
            List<AreaPojo> childCityList = Lists.newArrayList();
            for(AreaPojo cityPojo : cityList){
                if(provincePojo.getAreaId() == cityPojo.getParentId()){
                    cityPojo.setType(2);
                    childCityList.add(cityPojo);
                }
            }
            provincePojo.setParentId(0);
            provincePojo.setType(1);
            provincePojo.setChildList(childCityList);
        }

        return provinceList;
    }

}
