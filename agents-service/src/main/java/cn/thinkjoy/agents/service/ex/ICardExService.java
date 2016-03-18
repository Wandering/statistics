package cn.thinkjoy.agents.service.ex;

import cn.thinkjoy.agents.service.ex.common.IBaseExService;

import java.util.Map;

public interface ICardExService extends IBaseExService {

    public boolean goodsOutput(Map<String, Object> condition);

}
