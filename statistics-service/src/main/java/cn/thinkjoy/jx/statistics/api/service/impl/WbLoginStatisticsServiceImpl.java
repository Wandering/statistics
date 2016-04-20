package cn.thinkjoy.jx.statistics.api.service.impl;

import cn.thinkjoy.jx.statistics.dao.IDateDecreaseUserDAO;
import cn.thinkjoy.jx.statistics.service.IWbLoginStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhang on 2016/2/23.
 */
@Service("wbLoginStatisticsServiceImpl")
public class WbLoginStatisticsServiceImpl implements IWbLoginStatisticsService {

    @Autowired
    IDateDecreaseUserDAO dateDecreaseUserDAO;
    @Override
    public int insertWbLoginStatistics(Long userId,String userType) {
       return  dateDecreaseUserDAO.saveWebLoginStatistics(userId,userType);
    }
}
