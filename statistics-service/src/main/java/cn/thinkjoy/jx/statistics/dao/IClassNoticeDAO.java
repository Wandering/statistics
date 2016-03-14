package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.domain.ClassNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 * 日班级发送作业公告教师数统计
 */
public interface IClassNoticeDAO extends IBaseDAO<ClassNotice> {
    Integer totalCount(@Param("condition") Map<String,Object> var1 );
    Integer countByPageTime(@Param("condition") Map<String,Object> var1 );
    Integer countByPageTimeAndAreaId(@Param("condition") Map<String,Object> var1 );
    Integer countByPageTimeAndSchoolId(@Param("condition") Map<String,Object> var1 );
    List<ClassNotice> queryByTime(@Param("condition") Map<String,Object> var1);
    List<ClassNotice> queryByPageTime(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<ClassNotice> queryByPageTimeAndAreaId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<ClassNotice> queryByPageTimeAndSchoolId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<ClassNotice> queryByPageAndCondition(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
}