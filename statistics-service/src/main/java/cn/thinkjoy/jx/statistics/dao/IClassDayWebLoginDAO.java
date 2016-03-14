package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.domain.ClassDayLogin;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chenmeng on 16/2/19.
 *
 * 日班级登录人数汇总
 *
 */
public interface IClassDayWebLoginDAO extends IBaseDAO<ClassDayLogin> {
    Integer totalCount(@Param("condition") Map<String, Object> var1);
    Integer countByPageTime(@Param("condition") Map<String, Object> var1);
    Integer countByPageTimeAndAreaId(@Param("condition") Map<String, Object> var1);
    Integer countByPageTimeAndSchoolId(@Param("condition") Map<String, Object> var1);
    List<StatisticsPojo> queryByTime(@Param("condition") Map<String, Object> var1);
    List<StatisticsPojo> queryByPageTime(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<StatisticsPojo> queryByPageTimeAndAreaId(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<StatisticsPojo> queryByPageTimeAndSchoolId(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<ClassDayLogin> queryByPageAndCondition(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);


}
