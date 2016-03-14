package cn.thinkjoy.jx.statistics.service;

/**
 * Created by chenmeng on 2016/02/24.
 *
 */
public interface IAppActionStatisticsService {
    //客户端访问找家教，在线专家，名师面对面
    public int insertAppActionStatistics(Long userId, String userType, String actionType);
}
