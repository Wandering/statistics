/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  AreabatchLineServiceImpl.java 2015-12-16 09:46:54 $
 */
package cn.thinkjoy.agents.service.ex.impl;

import cn.thinkjoy.agents.dao.ex.ICardExDAO;
import cn.thinkjoy.agents.service.ex.ICardExService;
import cn.thinkjoy.agents.service.ex.common.AgentsConstant;
import cn.thinkjoy.agents.service.ex.common.AgentsInfoUtils;
import cn.thinkjoy.agents.service.ex.common.impl.BaseExService;
import cn.thinkjoy.domain.agents.Card;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("CardExServiceImpl")
@Scope("prototype")
public class CardExServiceImpl extends BaseExService implements ICardExService {
    @Autowired
    private ICardExDAO cardExDAO;
    @Override
    public ICardExDAO getDao() {
        return cardExDAO;
    }

    @Override
    public List<Map<String, Object>> queryPage(Map<String, Object> condition, int offset, int rows, String orderBy, String sortBy) {
        return getDao().queryPage(condition,offset,rows,orderBy,sortBy);
    }

    @Override
    public List<Map<String, Object>> queryPage(Map<String, Object> condition, int offset, int rows, String orderBy, String sortBy, Map<String, Object> selector) {
        return getDao().queryPage(condition,offset,rows,orderBy,sortBy,selector);
    }

    @Override
    public int count(Map<String, Object> condition) {
        return getDao().count(condition);
    }


    /**
     * 出库操作
     * @return
     */
    @Override
    @Deprecated
    public boolean goodsOutput(Map<String, Object> condition) {
        switch (AgentsInfoUtils.getAgentsUserArea().length()){
            case 0:
                condition.put("outputDate1",System.currentTimeMillis());
                break;
            case 2:
                condition.put("outputDate2",System.currentTimeMillis());
                break;
            case 4:
                condition.put("outputDate3",System.currentTimeMillis());
                break;
            case 6:
                break;
            default:
                break;
        }
        conditionHandler(condition);
        return getDao().output(condition)>0;
    }

    public List<Map<String, Object>> outPutCardNumber(Map<String, Object> condition){
        conditionHandler(condition);
        return getDao().outPutCardNumber(condition);
    }

    @Override
    public boolean hasAgentsArea(String goodsNumber) {
        return cardExDAO.hasAgentsArea(goodsNumber)>0;
    }


    @Override
    protected void mainDataHandler(List list) {
        AgentsInfoUtils.setGoodsStatusAndStorageDate(list);
//        AgentsInfoUtils.setFlow(list);
    }

    @Override
    protected void conditionHandler(Map<String, Object> condition) {
        if(AgentsInfoUtils.getUserWhereSql()!=null) {
            condition.put("whereSql", AgentsInfoUtils.getUserWhereSql());
        }
        condition.put("userArea", AgentsInfoUtils.getAgentsUserArea());
        condition.put("orderBy", "cardNumber");
        condition.put("sortBy", "asc");
    }

    @Override
    public void batchCreateCard(List<Card> cards) {
        cardExDAO.batchCreateCard(cards);
    }
}
