package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.domain.UserGroupChatDetail;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 *
 * 日班级用户聊天条数汇总
 *
 */
public interface IUserGroupChatDetailDAO extends IBaseDAO<UserGroupChatDetail> {
        Integer totalCount(@Param("condition") Map<String,Object> var1 );
        Integer countByPageTime(@Param("condition") Map<String,Object> var1 );
        Integer countByPageTimeAndAreaId(@Param("condition") Map<String,Object> var1 );
        Integer countByPageTimeAndSchoolId(@Param("condition") Map<String,Object> var1 );
        List<UserGroupChatDetail> queryByPageAndCondition(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
        List<StatisticsPojo> queryByTime(@Param("condition") Map<String,Object> var1);
        List<StatisticsPojo> queryByPageTime(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
        List<StatisticsPojo> queryByPageTimeAndAreaId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
        List<StatisticsPojo> queryByPageTimeAndSchoolId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);

}