package cn.thinkjoy.jx.statistics.controller.agents;

import cn.thinkjoy.agents.service.ex.IAreaExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<Map<String,Object>> getCurrUserNextArea(){
        return areaExService.getFlowNextArea();
    }

}
