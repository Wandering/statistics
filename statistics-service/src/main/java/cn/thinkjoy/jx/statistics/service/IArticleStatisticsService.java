package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.jx.statistics.pojo.ArticleStatisticsPojo;
import cn.thinkjoy.zgk.zgksystem.common.Page;

/**
 * 文章统计
 */
public interface IArticleStatisticsService {

    Page<ArticleStatisticsPojo> queryByPage(long beginTime, long endTime, int offset, int rows, String orderBy, String sortBy);

}

