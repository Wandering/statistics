package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.domain.ParentIncreaseDetail;
import cn.thinkjoy.jx.statistics.domain.TeacherIncreaseDetail;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zl on 2015/12/16.
 */
public interface IDateIncreaseUserDao extends IBaseDAO {

    Integer countByPageTime(@Param("condition")Map<String,Object> map);
    Integer countByPageTimeAndAreaId(@Param("condition")Map<String,Object> var1);
    Integer countByPageTimeAndSchoolId(@Param("condition")Map<String,Object> var1 );
    Integer countParentIncreaseUserDetail(@Param("condition")Map<String,Object> var1);
    Integer countTeacherIncreaseUserDetail(@Param("condition")Map<String,Object> var1);
    List<StatisticsPojo> queryByTime(@Param("condition")Map<String,Object> var1);
    List<StatisticsPojo> queryByPageTime(@Param("condition")Map<String,Object> var1,@Param("offset")int var2,@Param("rows")int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<StatisticsPojo> queryByPageTimeAndAreaId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<StatisticsPojo> queryByPageTimeAndSchoolId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<ParentIncreaseDetail> queryParentIncreaseUserDetail(@Param("condition")Map<String,Object> var1,@Param("offset")int var2,@Param("rows")int var3,@Param("sortBy")String var4);
    List<TeacherIncreaseDetail> queryTeacherIncreaseUserDetail(@Param("condition")Map<String,Object> var1,@Param("offset")int var2,@Param("rows")int var3,@Param("sortBy")String var4);
}
