package cn.thinkjoy.agents.dao.ex.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/3/18.
 */
public interface BaseCommonExDAO {
    /**
     * 增加排序支持
     * @param condition
     * @param offset
     * @param rows
     * @param orderBy
     * @param sortBy
     * @return
     */
    public List<Map<String,Object>> queryPage(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
                                              @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);


    public List<Map<String,Object>>  queryPage(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
                                               @Param("orderBy") String orderBy, @Param("sortBy") String sortBy, @Param("selector") Map<String, Object> selector);

    public int count(Map<String, Object> condition);
}
