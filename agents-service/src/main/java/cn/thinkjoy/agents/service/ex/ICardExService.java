package cn.thinkjoy.agents.service.ex;

import cn.thinkjoy.agents.dao.ex.ICardExDAO;
import cn.thinkjoy.agents.service.ex.common.IBaseExService;
import cn.thinkjoy.domain.agents.Card;

import java.util.List;
import java.util.Map;

public interface ICardExService extends IBaseExService {
    public ICardExDAO getDao();
    public boolean goodsOutput(Map<String, Object> condition);
    public List<Map<String, Object>> outPutCardNumber(Map<String, Object> condition);
    public boolean hasAgentsArea(String goodsNumber);

    /**
     * 批量生成卡片
     *
     * @param cards
     */
    void batchCreateCard(List<Card> cards);
}
