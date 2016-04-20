package cn.thinkjoy.jx.statistics.service;

/**
 * Created by jingqinghua on 2016/1/25.
 * addStatisticsPojo 20160217
 */
public interface IWbLoginStatisticsService {
    //web插入登陆统计数据
    public int insertWbLoginStatistics(Long userId,String userType);
}
