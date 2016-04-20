package cn.thinkjoy.agents.service.ex.impl;

import cn.thinkjoy.agents.dao.ex.ISeparateExDAO;
import cn.thinkjoy.agents.service.ex.ISeparateExService;
import cn.thinkjoy.jx.statistics.domain.pojo.MarketParamsPojo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zuohao on 16/4/19.
 */
@Service
public class SeparateExServiceImpl implements ISeparateExService {

    @Autowired
    ISeparateExDAO separateExDAO;

    @Override
    public MarketParamsPojo findSeparate() {
        return separateExDAO.findSeparate();
    }

    @Override
    public void updateSeparate(Integer levelProfits1, Integer levelProfits2) {
        Map<String,Object> map= Maps.newHashMap();
        map.put("splitPercentage",100-levelProfits1-levelProfits2);
        map.put("levelProfits",levelProfits1+"-"+levelProfits2);
        separateExDAO.updateSeparate(map);
    }
}
