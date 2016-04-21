package cn.thinkjoy.jx.statistics.api.service.impl;

import cn.thinkjoy.jx.statistics.dao.IDateDecreaseUserDAO;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IDecreaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mike on 2016/1/25.
 */
@Service("decreaseUserServiceImpl")
public class DecreaseUserServiceImpl implements IDecreaseUserService {
    @Autowired
    IDateDecreaseUserDAO dateDecreaseUserDAO;
    @Override
    public void addStatisticsPojo(StatisticsPojo statisticsPojo) {
        dateDecreaseUserDAO.saveStatistics(statisticsPojo);
    }
}
