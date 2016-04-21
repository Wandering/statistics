package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.jx.statistics.dao.IArticleStatisticsDAO;
import cn.thinkjoy.jx.statistics.pojo.ArticleStatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IArticleStatisticsService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jzli on 15/10/13.
 */
@Service("ArticleStatisticsServiceImpl")
public class ArticleStatisticsServiceImpl implements IArticleStatisticsService {

    @Autowired
    private IArticleStatisticsDAO articleStatisticsDAO;

    @Override
    public Page<ArticleStatisticsPojo> queryByPage(long beginTime, long endTime, int offset, int rows, String orderBy, String sortBy) {
        Page<ArticleStatisticsPojo> page = new Page<>();
        int count = articleStatisticsDAO.countArticleStatistics(beginTime, endTime);
        if(count > 0){
            page.setCount(count);
            page.setList(articleStatisticsDAO.queryArticleStatistics(beginTime, endTime, offset, rows, orderBy, sortBy));
        }
        return page;
    }
}
