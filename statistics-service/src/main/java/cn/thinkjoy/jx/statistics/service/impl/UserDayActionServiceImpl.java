package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IUserDayActionDAO;
import cn.thinkjoy.jx.statistics.domain.UserDayLogin;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IUserDayActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by chenmeng on 16/3/1.
 *
 * 行为统计访问汇总
 *
 */
@Service("UserDayActionServiceImpl")
public class UserDayActionServiceImpl extends AbstractPageService<IBaseDAO<UserDayLogin>,UserDayLogin> implements IUserDayActionService<IBaseDAO<UserDayLogin>,UserDayLogin> {
    @Autowired
    private IUserDayActionDAO userDayActionDAO;

    @Override
    public IBaseDAO<UserDayLogin> getDao() {
        return userDayActionDAO;
    }


    /**
     * @param map
     * @return
     */
    public List<StatisticsPojo> queryByTime(Map<String, Object> map) {
        List<StatisticsPojo> list = userDayActionDAO.queryByTime(map);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }


}