package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.jx.statistics.dao.IDateDecreaseUserDAO;
import cn.thinkjoy.jx.statistics.service.IAppActionStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenmeng on 2016/3/4.
 */
@Service("appActionStatisticsServiceImpl")
public class AppActionStatisticsServiceImpl implements IAppActionStatisticsService {

    @Autowired
    IDateDecreaseUserDAO dateDecreaseUserDAO;

    @Override
    public int insertAppActionStatistics(Long userId, String userType, String actionType) {
       return dateDecreaseUserDAO.saveAppActiontatistics(userId,userType,actionType);
    }
}
