package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 2016/1/25.
 */
public interface IDateDecreaseUserDAO extends IBaseDAO {
    public List<StatisticsPojo> queryByTime(@Param("condition")Map<String, Object> map);
    public List<StatisticsPojo>  queryByAreaIds(@Param("condition")Map<String, Object> map,@Param("offset")int var2,@Param("rows")int var3);
    public List<StatisticsPojo>  queryBySchoolIds(@Param("condition")Map<String, Object> map,@Param("offset")int var2,@Param("rows")int var3);
    public List<StatisticsPojo>  queryByClassIds(@Param("condition")Map<String, Object> map,@Param("offset")int var2,@Param("rows")int var3);
    public int saveStatistics(StatisticsPojo statisticsPojo);
    public  StatisticsPojo getStatisticsByUserId(@Param("userId") Long userId);
    //user登陆时候调用Web登陆人数统计日志
    public  int saveWebLoginStatistics(@Param("userId") Long userId,@Param("userType") String userType);

    //客户端登陆时行为统计的插入 找家教，在线专家，名师面对面
    public  int saveAppActiontatistics(@Param("userId") Long userId,@Param("userType") String userType,@Param("actionType")String actionType);
}
