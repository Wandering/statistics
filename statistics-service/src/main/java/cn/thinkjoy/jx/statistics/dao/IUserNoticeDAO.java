package cn.thinkjoy.jx.statistics.dao;


import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.domain.UserNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 *
 * 日班级教师发送作业公告个数汇总
 *
 */
public interface IUserNoticeDAO extends IBaseDAO<UserNotice> {
    Integer totalCount(@Param("condition") Map<String,Object> var1 );
    Integer countByPageTime(@Param("condition") Map<String,Object> var1 );
    Integer countByPageTimeAndAreaId(@Param("condition") Map<String,Object> var1 );
    Integer countByPageTimeAndSchoolId(@Param("condition") Map<String,Object> var1 );
    List<UserNotice> queryByTime(@Param("condition") Map<String,Object> var1);
    List<UserNotice> queryByPageTime(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<UserNotice> queryByPageTimeAndAreaId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<UserNotice> queryByPageTimeAndSchoolId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<UserNotice> queryByPageAndCondition(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
}
