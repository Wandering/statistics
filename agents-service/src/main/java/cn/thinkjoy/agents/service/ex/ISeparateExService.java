package cn.thinkjoy.agents.service.ex;

import cn.thinkjoy.jx.statistics.domain.pojo.MarketParamsPojo;

/**
 * Created by zuohao on 16/4/19.
 */
public interface ISeparateExService {
    MarketParamsPojo findSeparate();

    void updateSeparate(Integer levelProfits1,Integer levelProfits2);
}
