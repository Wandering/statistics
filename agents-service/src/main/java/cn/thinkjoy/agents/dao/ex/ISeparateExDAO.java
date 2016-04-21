package cn.thinkjoy.agents.dao.ex;

import cn.thinkjoy.agents.dao.ex.common.BaseCommonExDAO;
import cn.thinkjoy.jx.statistics.domain.pojo.MarketParamsPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/4/19.
 */
public interface ISeparateExDAO extends BaseCommonExDAO{
    List<MarketParamsPojo> findSeparate();

    void updateSeparate(Map<String,Object> map);
}
