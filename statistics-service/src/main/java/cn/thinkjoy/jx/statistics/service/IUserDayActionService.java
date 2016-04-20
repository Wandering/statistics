package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by cheneng on 16/03/01.
 *
 * 行为统计访问汇总
 *
 */
public interface IUserDayActionService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
    List<StatisticsPojo> queryByTime(Map<String, Object> map);
}
