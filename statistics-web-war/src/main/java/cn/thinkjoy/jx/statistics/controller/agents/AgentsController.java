package cn.thinkjoy.jx.statistics.controller.agents;

import cn.thinkjoy.agents.service.ICardService;
import cn.thinkjoy.agents.service.ex.ICardExService;
import cn.thinkjoy.agents.service.ex.common.AgentsInfoUtils;
import cn.thinkjoy.agents.service.ex.common.UserInfoContext;
import cn.thinkjoy.agents.util.ModelUtil;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.utils.ObjectFactory;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.domain.agents.Card;
import cn.thinkjoy.jx.statistics.controller.agents.common.BaseCommonController;
import cn.thinkjoy.jx.statistics.util.RandomCodeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhwang on 15/9/2.
 */
@Controller
@RequestMapping("/admin")
public class AgentsController extends BaseCommonController <ICardExService>{

    @Autowired
    private ICardExService cardExService;

    @Autowired
    private ICardService cardService;

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
    @Deprecated
    public Object queryPage(@RequestParam(required = false) String cardNumber,
                            @RequestParam(required = false) String area,
                            @RequestParam(required = false,defaultValue = "-1") long startDate,
                            @RequestParam(required = false,defaultValue = "-1") long endDate,
                            @RequestParam(required = false,defaultValue = "-1") int productType,
                            @RequestParam(required = false,defaultValue = "false") Boolean isOutput,
                            @RequestParam(required = false,defaultValue = "1",value = "currentPageNo") Integer page,
                            @RequestParam(required = false,defaultValue = "10",value = "pageSize") Integer rows){
        Map<String,Object> condition=new HashMap<>();
        condition.put("cardNumber",cardNumber);
        condition.put("productType",productType);
        condition.put("startDate",startDate);
        condition.put("endDate",endDate);

        if(isOutput){
            condition.put("output",isOutput);
            if(StringUtils.isNotEmpty(area)){
                condition.put("flowlist",areaToList(area));
            }
        }else {
            condition.put("notoutput",isOutput);
        }

        Map<String,Object> userInfo = UserInfoContext.getCurrentUserInfo();
        int roleType = Integer.valueOf(userInfo.get("roleType").toString());
        switch (roleType){
            case 1:
                condition.put("queryDte","outputDate1");
                break;
            case 2:
                condition.put("queryDte","outputDate2");
                break;
            case 3:
                condition.put("queryDte","outputDate3");
                break;
            default:
                condition.put("queryDte","outputDate1");
                break;
        }

        Map<String,Object> dataMap=doPage(page,rows,condition);
        if(isOutput){
            if(dataMap.containsKey("list") && dataMap.get("list")!=null) {
                AgentsInfoUtils.setFlow((List<Map<String,Object>>)dataMap.get("list"));
            }
        }

        return dataMap;
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
    @Deprecated
    @ResponseBody
    @RequestMapping(value = "/output")
    public Object output(@RequestParam("area")String flow,
                         @RequestParam(value = "outputList",required = false)String idlist,
                         @RequestParam(value = "productType",required = false,defaultValue = "-1")Integer productType,
                         @RequestParam(value = "rows",required = false)Integer rows){
        Map<String,Object> condition=new HashMap<>();
        condition.put("flow",flow);
        condition.put("productType",productType);
        if(idlist!=null){

            condition.put("idlist",idlist);
        }else if(rows!=null){
            condition.put("rows",rows);
        }else {
            throw new BizException("error","参数outputList和rows至少有一个不能为空");
        }
        condition.put("userArea", AgentsInfoUtils.getAgentsUserArea());
        if(!cardExService.hasAgentsArea(AgentsInfoUtils.getAgentsUserArea()+flow)){
            throw new BizException("error","出库失败，当前出库区域没有代理商！");
        }

        return cardExService.goodsOutput(condition);
    }

    /**
     * 出库操作
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/outPutCardNumber")
    public Object outPutCardNumber(@RequestParam(value = "rows")Integer rows,
                                   @RequestParam(value = "productType")Integer productType){
        Map<String,Object> condition=new HashMap<>();
        condition.put("rows",rows);
        condition.put("productType",productType);
        List<Map<String,Object>> maps=cardExService.outPutCardNumber(condition);
        Map<String,Object> resultMap=new HashMap<>();
        String start=null;
        String end=null;
        if(maps.size()>0){
            start=maps.get(0).get("cardNumber").toString();
        }
        if(maps.size()>1){
            end=maps.get(maps.size()-1).get("cardNumber").toString();
        }
        if(start!=null && end==null){
            end=maps.get(0).get("cardNumber").toString();
        }
        resultMap.put("start",start);
        resultMap.put("end",end);
        resultMap.put("count",maps.size());
        return resultMap;
    }

    @ResponseBody
    @ApiDesc(value = "批量生成卡片",owner = "杨国荣")
    @RequestMapping(value = "/batchCreateCard",method = RequestMethod.GET)
    public Object batchCreateCard(@RequestParam(value = "count") Integer count,
                                   @RequestParam(value = "productType") Integer productType){
        /**
         * productType:套餐类型
         *   1:金榜登科  GK6
         *   2:状元及第  GK8
         *   3:金榜题名  GK7
         */
        Map<String,Object> queryMap = Maps.newHashMap();
        queryMap.put("status",0);
        queryMap.put("productType",productType);
        Card tempCard = (Card) cardService.queryOne(queryMap,"cardNumber",SqlOrderEnum.DESC);
        // 卡号生成规则:库存最大卡号依次向后累加
        String prefix = "GK";
        String number = StringUtils.replace(tempCard.getCardNumber(),prefix,"");

        List<Card> cards = Lists.newArrayList();
        for(int i=1;i<=count;i++){
            Card card = new Card();
            ModelUtil.initBuild(card);
            card.setCardNumber(prefix+(Long.valueOf(number)+i));
            card.setPassword(RandomCodeUtil.generateCharCode(10).toLowerCase());
            card.setCardType("1");
            card.setProductType(productType);
            cards.add(card);
        }
        cardExService.batchCreateCard(cards);
        return ObjectFactory.getSingle();
    }

    @Override
    protected Map<String, Object> getSelector() {
        Map<String,Object> selector=new HashMap<>();
        selector.put("goodsNumber","goodsNumber");
        selector.put("id","id");
        selector.put("cardNumber","cardNumber");
        selector.put("createDate","createDate");
        selector.put("outputDate1","outputDate1");
        selector.put("outputDate2","outputDate2");
        selector.put("outputDate3","outputDate3");
        selector.put("activeDate","activeDate");
        selector.put("productType","productType");
        return selector;
    }

    private List areaToList(String area){
        String[] areas = area.split(",");
        List<String> list=new ArrayList<>();
        for(String str:areas){
            list.add(str);
        }
        return list;
    }

    @Override
    protected SqlOrderEnum getSqlOrder() {
        return SqlOrderEnum.ASC;
    }

    @Override
    protected String getDefaultOrderBy() {
        return "cardNumber";
    }
}
