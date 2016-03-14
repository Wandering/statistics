package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.domain.OpFans;
import cn.thinkjoy.jx.statistics.service.IOpFansService;
import cn.thinkjoy.jx.statistics.service.IOpStatisticsService;
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
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wangyongqiang on 15/9/21
 *
 *欠费统计
 *
 */
@Controller
@RequestMapping("/statistics/fans")
public class OpFansStatisticsController {

    private static Logger LOGGER = LoggerFactory.getLogger(OpFansStatisticsController.class);

    @Autowired
    private IOpFansService opFansService;
    @Autowired
    private IOpStatisticsService iOpStatisticsService;

    /**
     * 粉丝数统计
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryOpFans",method = RequestMethod.GET)
    public Page<OpFans> queryOpFans(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay=request.getParameter("dateDay");
        if(StringUtils.isBlank(dateDay)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("dayTime",dateDay);
        Page<OpFans> page = opFansService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize,"userId", SqlOrderEnum.ASC.getAction());
        if(page == null || page.getCount() == null || page.getCount() == 0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    @RequestMapping(value = "queryOpFansByClassfy", method = RequestMethod.GET)
    @ResponseBody
    public Page<OpFans> queryOpFansByClassfy(HttpServletRequest request) {
        int currentPageNo = ServletRequestUtils.getIntParameter(request, "currentPageNo", 1);
        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
        String dateDay = request.getParameter("dateDay");
        long classfyId = ServletRequestUtils.getLongParameter(request, "classfyId", 0);
        if(StringUtils.isBlank(dateDay) || classfyId == 0){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Page<OpFans> page = opFansService.queryByPageAndClassfy(dateDay, classfyId,
                (currentPageNo - 1) * pageSize, pageSize, "id", SqlOrderEnum.ASC.getAction());
        if (page == null || page.getCount() == null || page.getCount() == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    @RequestMapping(value = "exportOpFansByClassfy", method = RequestMethod.GET)
    @ResponseBody
    public void exportOpFansByClassfy(HttpServletRequest request, HttpServletResponse response) {
        String dateDay = request.getParameter("dateDay");
        long classfyId = ServletRequestUtils.getLongParameter(request, "classfyId", 0);
        if(StringUtils.isBlank(dateDay) || classfyId == 0){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        String classfyName = "";
        List<Map<String,Object>>  classfyMap= iOpStatisticsService.getClassfyInfo();
        if (classfyMap != null) {
            for (Map<String, Object> m: classfyMap) {
                BigInteger cid = (BigInteger) m.get("id");
                if (cid.longValue() == classfyId) {
                    classfyName = (String) m.get("name");
                }
            }
        }

        Page<OpFans> page = opFansService.queryByPageAndClassfy(dateDay, classfyId,
                0, 1000, "id", SqlOrderEnum.ASC.getAction());
        ExcelUtil excel = new ExcelUtil();
        excel.createBookAndSheet(true, "Sheet1");
        // 写入表头
        excel.writeRow(0, "账号", "关注人数");
        if (page != null) {
            List<OpFans> data = page.getList();
            if (data!=null && data.size()>0) {
                int rowNum = 0;
                for (OpFans opFans: data) {
                    rowNum++;
                    excel.writeRow(rowNum, opFans.getUserName(), opFans.getNum());
                }
            }
        }
        DownloadUtil.downloadXls(response, dateDay+classfyName+"公众号粉丝数", excel.getInputStream());
    }

}
