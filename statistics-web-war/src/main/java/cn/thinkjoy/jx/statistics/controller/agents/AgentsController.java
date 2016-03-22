package cn.thinkjoy.jx.statistics.controller.agents;

import cn.thinkjoy.agents.service.ICardService;
import cn.thinkjoy.agents.service.ex.ICardExService;
import cn.thinkjoy.agents.service.ex.common.AgentsInfoUtils;
import cn.thinkjoy.agents.service.ex.common.IBaseExService;
import cn.thinkjoy.jx.statistics.controller.agents.common.BaseCommonController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhwang on 15/9/2.
 */
@Controller
@RequestMapping("/admin")
public class AgentsController extends BaseCommonController <ICardExService>{
    @Autowired
    private ICardExService cardExService;

    /**
     *  获取当前用户货物信息
     * @param cardNumber 卡号非必填
     * @param area 流向区域 非必填格式（当前用户下一级流向代码为xx0000、xxxx00、xxxxxx）
     * @param isOutput 非必填默认未出库 未出库/已出库 true/false
     * @param page 当前页 默认1
     * @param rows 展示条数默认10
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/agents")
    public Object queryPage(@RequestParam(required = false)String cardNumber,
                            @RequestParam(required = false)String area,
                            @RequestParam(required =false,defaultValue = "false")Boolean isOutput,
                            @RequestParam(required=false,defaultValue = "1") Integer page,
                            @RequestParam(required=false,defaultValue = "10") Integer rows){
        Map<String,Object> condition=new HashMap<>();
        if(StringUtils.isNotEmpty(cardNumber)){
            condition.put("cardNumber",cardNumber);
        }
        if(isOutput){
            condition.put("output",isOutput);
            if(StringUtils.isNotEmpty(area)){
                condition.put("flow",area);
            }
        }else {
            condition.put("notoutput",isOutput);
        }

        return doPage(page,rows,condition);
    }

    @Override
    protected ICardExService getService() {
        return cardExService;
    }

    @Override
    protected void enhanceCondition(Map condition) {
        super.enhanceCondition(condition);
    }

    /**
     * 出库操作
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/output")
    public Object output(@RequestParam("area")String flow,@RequestParam(value = "outputList",required = false)String idlist,@RequestParam(value = "rows",required = false)Integer rows){
        Map<String,Object> condition=new HashMap<>();
        condition.put("flow",flow);
        if(idlist!=null){

            condition.put("idlist",idlist);
        }

        condition.put("userArea", AgentsInfoUtils.getAgentsUserArea());
        if(rows!=null){
            condition.put("rows",rows);
        }
        return cardExService.goodsOutput(condition);
    }

}
