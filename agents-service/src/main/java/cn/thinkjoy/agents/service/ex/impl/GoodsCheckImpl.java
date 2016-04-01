package cn.thinkjoy.agents.service.ex.impl;

import cn.thinkjoy.agents.IGoodsCheck;
import cn.thinkjoy.agents.dao.ex.ICardExDAO;
import cn.thinkjoy.agents.service.ex.ICardExService;
import cn.thinkjoy.agents.service.ex.common.AgentsInfoUtils;
import cn.thinkjoy.domain.agents.Card;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/3/19.
 */
@Service("GoodsCheckImpl")
@Scope("prototype")
public class GoodsCheckImpl implements IGoodsCheck {
    @Autowired
    private ICardExDAO cardExDAO;
    @Override
    public void goodsCheck(Map<String,Object> map) {
        Map<String,Object> condition=new HashMap<>();
        Integer status=0;
        if(map.containsKey("cardNumber")) {
            String cardNumber=map.get("cardNumber").toString();
            String selgoodsNumber=cardExDAO.selectCardByUid(cardNumber);
            String goodsNumber=AgentsInfoUtils.addZeroForNum(selgoodsNumber, 6);
            if(lastAgentsArea(selgoodsNumber)) {
                if (map.containsKey("userId")) {
                    Map<String, Object> userArea = cardExDAO.selectByUid(map.get("userId").toString());
                    if (userArea.containsKey("countyId")) {
                        if (goodsNumber.equals(userArea.get("countyId"))) {
                            status = 0;
                        } else {
                            status = 1;
                        }
                    } else if (userArea.containsKey("cityId")) {
                        if (goodsNumber.equals(userArea.get("cityId"))) {
                            status = 0;
                        } else {
                            status = 1;
                        }
                    } else if (userArea.containsKey("provinceId")) {
                        if (goodsNumber.equals(userArea.get("provinceId"))) {
                            status = 0;
                        } else {
                            status = 1;
                        }
                    }

                }
            }else {
                Map<String, Object> userArea = cardExDAO.selectByUid(map.get("userId").toString());
                switch (selgoodsNumber.length()) {
                    case 0:
                        status = 1;
                        break;
                    case 2:
                        if (userArea.containsKey("provinceId")) {
                            if (goodsNumber.equals(userArea.get("provinceId"))) {
                                status = 0;
                            } else {
                                status = 1;
                            }
                        }
                        break;
                    case 4:
                        if (userArea.containsKey("cityId")) {
                            if (goodsNumber.equals(userArea.get("cityId"))) {
                                status = 0;
                            } else {
                                status = 1;
                            }
                        }
                        break;
                    case 6:
                        if (userArea.containsKey("countyId")) {
                            if (goodsNumber.equals(userArea.get("countyId"))) {
                                status = 0;
                            } else {
                                status = 1;
                            }
                        }
                        break;
                    default:
                        status = 1;
                        break;
                }
            }

            condition.put("activeDate", System.currentTimeMillis());
            condition.put("errorStatus", status);
            condition.put("cardNumber",cardNumber);
            Card card=cardExDAO.getCardById(cardNumber);
            if (card!=null&&StringUtils.isNotBlank(card.getGoodsNumber())) {
                cardExDAO.active(condition);
            }
        }
    }

    /**
     * 判断当前卡所在代理商有没有下级
     * @param goodsNumber
     * @return
     */
    private boolean lastAgentsArea(String goodsNumber){
        return cardExDAO.lastAgentsArea(goodsNumber)>0;
    }


}
