package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.jx.statistics.dao.IOpStatisticsDao;
import cn.thinkjoy.jx.statistics.domain.OpAccount;
import cn.thinkjoy.jx.statistics.service.IOpStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/10/13.
 */
@Service("opStatisticsServiceImpl")
public class OpStatisticsServiceImpl implements IOpStatisticsService {
    @Autowired
    private IOpStatisticsDao opStatisticsDao;
    @Override
    public List<Map<String, Object>> getClassfyInfo() {
        return opStatisticsDao.getClassfyInfo();
    }

    @Override
    public List<OpAccount> getAccountInfoByClassfyId(Long classfyId) {
        return opStatisticsDao.getAccountInfoByClassfyId(classfyId);
    }

    @Override
    public List<Map<String, Object>> getAccountFans(Long userId, String dateStart, String dateEnd) {
        return opStatisticsDao.getAccountFans(userId,dateStart,dateEnd);
    }

    @Override
    public List<Map<String, Object>> getAccountArticles(Long userId, String dateStart, String dateEnd) {
        return opStatisticsDao.getAccountArticles(userId,dateStart,dateEnd);
    }

    @Override
    public List<Map<String, Object>> getAccountEvents(Long userId, String dateStart, String dateEnd) {
        return opStatisticsDao.getAccountEvents(userId,dateStart,dateEnd);
    }
}
