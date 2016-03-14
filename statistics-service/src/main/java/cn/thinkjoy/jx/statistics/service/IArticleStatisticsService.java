package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.domain.UserNotice;
import cn.thinkjoy.jx.statistics.domain.pojo.ArticleStatisticsPojo;

import java.util.List;
import java.util.Map;

/**
 * 文章统计
 */
public interface IArticleStatisticsService {

    Page<ArticleStatisticsPojo> queryByPage(long beginTime, long endTime, int offset, int rows, String orderBy, String sortBy);

}

