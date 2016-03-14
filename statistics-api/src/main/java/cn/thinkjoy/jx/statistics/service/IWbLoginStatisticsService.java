package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;

/**
 * Created by jingqinghua on 2016/1/25.
 * addStatisticsPojo 20160217
 */
public interface IWbLoginStatisticsService {
    //web插入登陆统计数据
    public int insertWbLoginStatistics(Long userId,String userType);
}
