package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.domain.UserDayLogin;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chenmeng on 16/1/19.
 *
 * 日班级用户登录次数汇总
 *
 */
public interface IUserDayWebLoginDAO extends IBaseDAO<UserDayLogin> {
    Integer totalCount(@Param("condition") Map<String, Object> var1);
    Integer countByPageTime(@Param("condition") Map<String, Object> var1);
    Integer countByPageTimeAndAreaId(@Param("condition") Map<String, Object> var1);
    Integer countByPageTimeAndSchoolId(@Param("condition") Map<String, Object> var1);
    List<StatisticsPojo> queryByTime(@Param("condition") Map<String, Object> var1);
    List<StatisticsPojo> queryByPageTime(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<StatisticsPojo> queryByPageTimeAndAreaId(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<StatisticsPojo> queryByPageTimeAndSchoolId(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<UserDayLogin> queryByPageAndCondition(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    /** 登录用户分布数据 */
    StatisticsPojo queryLoginUserCountByTime(@Param("condition") Map<String, Object> condition);
    /** 登录用户分布数据(按地区) */
    List<StatisticsPojo> queryLoginUserCountByPageTimeAndAreaId(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows);
    Integer countLoginUserCountByPageTimeAndAreaId(@Param("condition") Map<String, Object> condition);
    /** 登录用户分布数据(按学校) */
    List<StatisticsPojo> queryLoginUserCountByPageTimeAndSchoolId(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows);
    Integer countLoginUserCountByPageTimeAndSchoolId(@Param("condition") Map<String, Object> condition);
    /** 登录用户分布数据(按班级) */
    List<StatisticsPojo> queryLoginUserCountByPageTimeAndClassId(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows);
    Integer countLoginUserCountByPageTimeAndClassId(@Param("condition") Map<String, Object> condition);
}
