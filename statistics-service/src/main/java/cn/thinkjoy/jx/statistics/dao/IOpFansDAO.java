package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.domain.OpFans;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 * 服务账户日粉丝数统计
 *
 */
public interface IOpFansDAO extends IBaseDAO<OpFans> {
    Integer totalCount(@Param("condition") Map<String,Object> var1 );

    List<OpFans> queryByPageAndCondition(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);

    /** 不同分类下的公众号粉丝统计 */
    List<OpFans> quertByPageAndClassfy(@Param("classfyId") long classfyId, @Param("dayTime") String dayTime,
                                       @Param("offset") int offset, @Param("rows") int rows, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);
    Integer countByPageAndClassfy(@Param("classfyId") long classfyId, @Param("dayTime") String dayTime);
}

