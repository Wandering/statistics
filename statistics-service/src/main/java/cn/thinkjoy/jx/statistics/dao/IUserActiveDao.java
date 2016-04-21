package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.pojo.UserActiveInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 2016/1/16.
 */
public interface IUserActiveDao extends IBaseDAO<UserActiveInfo> {
    List<UserActiveInfo> queryByPage(@Param("condition") Map<String,Object> var1,@Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);
    Integer countByPage();
}
