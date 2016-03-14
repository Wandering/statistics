package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.domain.OpFans;
import cn.thinkjoy.jx.statistics.domain.pojo.ArticleStatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IArticleStatisticsService;
import cn.thinkjoy.jx.statistics.util.DateUtil;
import cn.thinkjoy.jx.statistics.util.DownloadUtil;
import cn.thinkjoy.jx.statistics.util.ExcelUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jzli on 15/10/13.
 */
@Controller
@RequestMapping("/statistics/article")
public class ArticleStatisticsController {
    private static Logger LOGGER = LoggerFactory.getLogger(ArticleStatisticsController.class);
    @Autowired
    private IArticleStatisticsService articleStatisticsService;

    @RequestMapping(value = "queryArticle", method = RequestMethod.GET)
    @ResponseBody
    public Page<ArticleStatisticsPojo> queryArticleByPage(HttpServletRequest request) {


        int currentPageNo = ServletRequestUtils.getIntParameter(request, "currentPageNo", 1);
        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if(StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        long beginTime = DateUtil.StringDateToLong(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long endTime = DateUtil.StringDateToLong(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
        Page<ArticleStatisticsPojo> page = articleStatisticsService.queryByPage(beginTime, endTime,
                (currentPageNo - 1) * pageSize, pageSize, "sm.createDate", SqlOrderEnum.DESC.getAction());
        if (page == null || page.getCount() == null || page.getCount() == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    @RequestMapping(value = "exportArticle", method = RequestMethod.GET)
    @ResponseBody
    public void exportArticleByPage(HttpServletRequest request, HttpServletResponse response) {

        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if(StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        long beginTime = DateUtil.StringDateToLong(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long endTime = DateUtil.StringDateToLong(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
        Page<ArticleStatisticsPojo> page = articleStatisticsService.queryByPage(beginTime, endTime,
                0, 10000, "sm.createDate", SqlOrderEnum.DESC.getAction());

        ExcelUtil excel = new ExcelUtil();
        excel.createBookAndSheet(true, "Sheet1");
        // 写入表头
        excel.writeRow(0, "公众账号", "文章主题", "审核时间", "推送人数", "浏览量");
        if (page != null) {
            List<ArticleStatisticsPojo> data = page.getList();
            if (data!=null && data.size()>0) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                int rowNum = 0;
                for (ArticleStatisticsPojo a: data) {
                    rowNum++;
                    excel.writeRow(rowNum, a.getUserName(), a.getSubject(), format.format(new Date(a.getReviewTime())), a.getReadCount(), a.getHit());
                }
            }
        }
        DownloadUtil.downloadXls(response, beginDate + "-" + endDate + "文章统计", excel.getInputStream());
    }
}
