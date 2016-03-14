package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 2016/1/25.
 */
public interface IDateDecreaseUserService {

    List<StatisticsPojo> queryByTime(Map<String, Object> map);
    Page<StatisticsPojo> queryByAreaIds(Map<String, Object> map,int offset,int rows);
    Page<StatisticsPojo> queryBySchoolIds(Map<String, Object> map,int offset,int rows);
    Page<StatisticsPojo> queryByClassIds(Map<String, Object> map,int offset,int rows);
}
