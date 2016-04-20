package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.jx.statistics.pojo.ArticleStatisticsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jzli on 15/10/13.
 * 文章统计
 */
public interface IArticleStatisticsDAO {

    /** 文章统计接口 */
    public List<ArticleStatisticsPojo> queryArticleStatistics(
            @Param("beginTime") long startTime, @Param("endTime") long endTime,
            @Param("offset") int offset, @Param("rows") int rows, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);
    public int countArticleStatistics(@Param("beginTime") long beginTime, @Param("endTime") long endTime);
}
