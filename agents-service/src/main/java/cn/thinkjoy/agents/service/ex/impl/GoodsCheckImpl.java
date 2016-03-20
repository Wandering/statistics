package cn.thinkjoy.agents.service.ex.impl;

import cn.thinkjoy.agents.IGoodsCheck;
import cn.thinkjoy.agents.dao.ex.ICardExDAO;
import cn.thinkjoy.agents.service.ex.common.AgentsInfoUtils;
import cn.thinkjoy.agents.service.ex.common.AreaCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/3/19.
 */
@Service("GoodsCheckImpl")
public class GoodsCheckImpl implements IGoodsCheck {
    @Autowired
    private ICardExDAO cardExDAO;
    @Override
    public void goodsCheck(Map<String,Object> map) {
        Map<String,Object> condition=new HashMap<>();
        Integer status=0;
        if(map.containsKey("cardNumber")) {
            String cardNumber=map.get("cardNumber").toString();
            String goodsNumber=AgentsInfoUtils.addZeroForNum(cardExDAO.selectCardByUid(cardNumber), 6);
        if(map.containsKey("userId")) {
            Map<String,Object> userArea=cardExDAO.selectByUid(map.get("userId").toString());
            if(userArea.containsKey("countyId")){
                if(goodsNumber.equals(userArea.get("countyId"))){
                    status=0;
                }else {
                    status=1;
                }
            }else if(userArea.containsKey("cityId")){
                if(goodsNumber.equals(userArea.get("cityId"))){
                    status=0;
                }else {
                    status=1;
                }
            }else if(userArea.containsKey("provinceId")){
                if(goodsNumber.equals(userArea.get("provinceId"))){
                    status=0;
                }else {
                    status=1;
                }
            }

        }

            condition.put("activeDate", System.currentTimeMillis());
            condition.put("errorStatus", status);
            condition.put("cardNumber",cardNumber);
            cardExDAO.active(condition);
        }


//        System.out.println("test");
    }
}
