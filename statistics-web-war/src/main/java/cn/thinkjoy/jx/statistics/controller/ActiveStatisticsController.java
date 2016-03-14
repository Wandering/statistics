package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.domain.pojo.UserActiveInfo;
import cn.thinkjoy.jx.statistics.service.UserActiveService;
import cn.thinkjoy.jx.statistics.util.DateUtil;
import cn.thinkjoy.jx.statistics.util.DownloadUtil;
import cn.thinkjoy.jx.statistics.util.ExcelUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 2016/1/16.
 * 活跃统计
 */
@Controller
@RequestMapping("/statistics/active")
public class ActiveStatisticsController {
    @Autowired
    UserActiveService userActiveService;

    private static Logger LOGGER = LoggerFactory.getLogger(ActiveStatisticsController.class);


    /**
     *根据日期统计用户活跃度
     * @param request request
     * @return Page<T>
     */
    @RequestMapping(value = "/queryUserActiveInfo",method = RequestMethod.GET)
    @ResponseBody
    public Page<UserActiveInfo> queryUserActiveInfo(HttpServletRequest request){
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }

        Map<String,Object> map=new HashMap<>();
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String startDay = request.getParameter("dateStart");
        String endDay = request.getParameter("dateEnd");
        long beginTime = DateUtil.StringDateToLong(startDay + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long endTime = DateUtil.StringDateToLong(endDay + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

        if (startDay != null && endTime < beginTime){
            throw new BizException(ERRORCODE.ENDDAY_LOW_STARTDAY_ERROR.getCode(), ERRORCODE.ENDDAY_LOW_STARTDAY_ERROR.getMessage());
        }
        map.put("startDay", beginTime);
        map.put("endDay", endTime);
        Page<UserActiveInfo> page = userActiveService.queryByPage(map, (currentPageNo - 1) * pageSize, pageSize, "activeDayNum", SqlOrderEnum.DESC.getAction());
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 导出开放平台用户活跃统计
     * @param request
     * @param response
     */
    @RequestMapping(value = "exportUserActiveStatistics", method = RequestMethod.GET)
    @ResponseBody
    public void exportUserActiveStatistics(HttpServletRequest request, HttpServletResponse response){
        String startDay = request.getParameter("dateStart");
        String endDay = request.getParameter("dateEnd");
        if(StringUtils.isBlank(startDay) || StringUtils.isBlank(endDay)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        long beginTime = DateUtil.StringDateToLong(startDay + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long endTime = DateUtil.StringDateToLong(endDay+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Map map = new HashMap();
        map.put("startDay", beginTime);
        map.put("endDay", endTime);
        Page<UserActiveInfo> page = userActiveService.queryByPage(map, 0, 1000, "activeDayNum", SqlOrderEnum.DESC.getAction());
        ExcelUtil excel = new ExcelUtil();
        excel.createBookAndSheet(true, "Sheet1");
        // 写入表头
        excel.writeRow(0, "类型", "公众账号", "发布文章数", "发布活动数", "发布活动/文章天数", "活跃等级");
        if (page != null) {
            List<UserActiveInfo> data = page.getList();
            if (data!=null && data.size()>0) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                int rowNum = 0;
                String activeRank = "";
                for (UserActiveInfo a: data) {
                    rowNum++;

                    if (a.getActiveDayNum() == 0){
                        activeRank =  "★";
                    }
                    if (a.getActiveDayNum() >= 1 && a.getActiveDayNum() < 3){
                        activeRank =  "★★";
                    }
                    if (a.getActiveDayNum() >= 3 && a.getActiveDayNum() < 5){
                        activeRank =  "★★★";
                    }
                    if (a.getActiveDayNum() == 5 ){
                        activeRank =  "★★★★";
                    }
                    if (a.getActiveDayNum() >= 6){
                        activeRank =  "★★★★★";
                    }
                    excel.writeRow(rowNum, a.getClassfyName(), a.getUserName(), a.getArticleNum(), a.getActivityNum(), a.getActiveDayNum(), activeRank);
                }
            }
        }
        DownloadUtil.downloadXls(response, startDay + "-" + endDay + "公众号活跃统计", excel.getInputStream());
    }

}
