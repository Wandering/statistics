package cn.thinkjoy.agents.service.ex;

import cn.thinkjoy.agents.dao.ex.ICardExDAO;
import cn.thinkjoy.agents.service.ex.common.IBaseExService;

import java.util.List;
import java.util.Map;

public interface ICardExService extends IBaseExService {
    public ICardExDAO getDao();
    public boolean goodsOutput(Map<String, Object> condition);
    public List<Map<String, Object>> outPutCardNumber(Map<String, Object> condition);
    public boolean hasAgentsArea(String goodsNumber);

}
