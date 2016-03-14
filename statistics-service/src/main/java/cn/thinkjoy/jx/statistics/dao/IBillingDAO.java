package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;

import cn.thinkjoy.jx.statistics.domain.Billing;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 * 欠费数据汇总
 *
 */
public interface IBillingDAO extends IBaseDAO<Billing> {

    Integer totalCount(@Param("condition") Map<String,Object> var1 );
    Integer countByPage(@Param("condition") Map<String,Object> var1);
    Integer countByPageTimeAndAreaId(@Param("condition") Map<String,Object> var1 );
    Integer countByPageTimeAndSchoolId(@Param("condition") Map<String,Object> var1 );
    List<Billing> queryByPage(@Param("condition") Map<String,Object> var1,@Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<Billing> queryByPageTimeAndAreaId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    List<Billing> queryByPageTimeAndSchoolId(@Param("condition") Map<String,Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);

}
