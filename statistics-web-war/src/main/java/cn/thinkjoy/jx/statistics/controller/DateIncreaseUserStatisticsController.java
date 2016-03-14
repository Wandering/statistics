package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.domain.ParentIncreaseDetail;
import cn.thinkjoy.jx.statistics.domain.TeacherIncreaseDetail;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IDateIncreaseUserService;
import cn.thinkjoy.jx.statistics.util.Constants;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zl on 2015/12/16.
 */
@Controller
@RequestMapping("/statistics/increaseUser")
public class DateIncreaseUserStatisticsController {
    private static Logger LOGGER = LoggerFactory.getLogger(DateIncreaseUserStatisticsController.class);

    @Autowired
    private IDateIncreaseUserService dateIncreaseUserService;

    /**
     * 获取日增老师和学生折线图
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryIncreaseUser", method = RequestMethod.GET)
    public List<StatisticsPojo> queryIncreaseUser(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        if (null == userPojo) {
            throw new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(), ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        if (StringUtils.isBlank(dateStart) && StringUtils.isBlank(dateEnd)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        List<Long> areaList = userPojo.getAreaIds();
        if (null == areaList || areaList.size() == 0 || (areaList != null && areaList.size() == 1 && areaList.get(0) == 0)) {
            map.put("areaIds", null);
        } else {
            map.put("areaIds", areaList);
        }
        List<Long> schoolList = userPojo.getSchoolIds();
        if (null == schoolList || schoolList.size() == 0 || (null != schoolList && schoolList.size() == 1 && schoolList.get(0) == 0)) {
            map.put("schoolIds", null);
        } else {
            map.put("schoolIds", schoolList);
        }
        map.put("dateStart", dateStart);
        map.put("dateEnd", dateEnd);
        List<StatisticsPojo> list = dateIncreaseUserService.queryByTime(map);
        if (null != list && list.size() > 0) {
            return list;
        } else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 按照时间获取区域日增老师和学生数
     */
    @ResponseBody
    @RequestMapping(value = "queryAreaIncreaseUser", method = RequestMethod.GET)
    public Page<StatisticsPojo> queryAreaIncreaseUser(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        if (null == userPojo) {
            throw new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(), ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay = request.getParameter("dateDay");
        if (StringUtils.isBlank(dateDay)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }

        Map<String, Object> map = new HashMap<>();
        List<Long> areaList = userPojo.getAreaIds();
        if (null == areaList || areaList.size() == 0 || (areaList != null && areaList.size() == 1 && areaList.get(0) == 0)) {
            map.put("areaIds", null);
        } else {
            map.put("areaIds", areaList);
        }
        List<Long> schoolList = userPojo.getSchoolIds();
        if (null == schoolList || schoolList.size() == 0 || (null != schoolList && schoolList.size() == 1 && schoolList.get(0) == 0)) {
            map.put("schoolIds", null);
        } else {
            map.put("schoolIds", schoolList);
        }
        map.put("dateDay", dateDay);
        Page<StatisticsPojo> page = dateIncreaseUserService.queryByPageTime(map, (pageNo - 1) * pageSize, pageSize, "areaId", SqlOrderEnum.ASC.getAction(), Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM);
        if (null == page || page.getCount() == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        } else {
            return page;
        }
    }

    /**
     * 按照时间获取区域学校的日增老师和学生数
     */

    @ResponseBody
    @RequestMapping(value = "querySchoolIncreaseUser", method = RequestMethod.GET)
    public Page<StatisticsPojo> querySchoolIncreaseUser(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        if (null == userPojo) {
            throw new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(), ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay = request.getParameter("dateDay");
        String areaId = request.getParameter("areaId");
        if (StringUtils.isBlank(dateDay) && StringUtils.isBlank(areaId)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        List<Long> schoolList = userPojo.getSchoolIds();
        if (null == schoolList || schoolList.size() == 0 || (null != schoolList && schoolList.size() == 1 && schoolList.get(0) == 0)) {
            map.put("schoolIds", null);
        } else {
            map.put("schoolIds", schoolList);
        }
        map.put("dateDay", dateDay);
        map.put("areaId", Long.valueOf(areaId));
        Page<StatisticsPojo> page = dateIncreaseUserService.queryByPageTime(map, (pageNo - 1) * pageSize, pageSize, "schoolId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_AREAID_QUERY_SCHOOL_NUM);
        if (null == page || page.getCount() == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        } else {
            return page;
        }
    }

    /**
     * 按照时间获取班级的日增老师和学生数
     */
    @ResponseBody
    @RequestMapping(value = "queryClassIncreaseUser", method = RequestMethod.GET)
    public Page<StatisticsPojo> queryClassIncreaseUser(HttpServletRequest request) {
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay = request.getParameter("dateDay");
        String schoolId = request.getParameter("schoolId");
        if (StringUtils.isBlank(dateDay) && StringUtils.isBlank(schoolId)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dateDay", dateDay);
        map.put("schoolId", Long.valueOf(schoolId));
        Page<StatisticsPojo> page = dateIncreaseUserService.queryByPageTime(map, (pageNo - 1) * pageSize, pageSize, "classId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_SCHOOLID_QUERY_CLASS_NUM);
        if (null == page || page.getCount() == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        } else {
            return page;
        }
    }

    /**
     * 按照时间获取班级的日增家长详情
     */
    @ResponseBody
    @RequestMapping(value = "queryParentIncreaseUserDetail", method = RequestMethod.GET)
    public Page<ParentIncreaseDetail> queryParentIncreaseUserDetail(HttpServletRequest request) {
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay = request.getParameter("dateDay");
        String classId = request.getParameter("classId");
        if (StringUtils.isBlank(dateDay) && StringUtils.isBlank(classId)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dateDay", dateDay);
        map.put("classId", Long.valueOf(classId));
        Page<ParentIncreaseDetail> page = dateIncreaseUserService.queryParentIncreaseUserDetail(map, (pageNo - 1) * pageSize, pageSize, SqlOrderEnum.ASC.getAction());
        if (null == page || page.getCount() == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        } else {
            return page;
        }
    }
    /**
     * 按照时间获取班级的日增老师详情
     */
    @ResponseBody
    @RequestMapping(value = "queryTeacherIncreaseUserDetail", method = RequestMethod.GET)
    public Page<TeacherIncreaseDetail> queryTeacherIncreaseUserDetail(HttpServletRequest request) {
        int pageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay = request.getParameter("dateDay");
        String classId = request.getParameter("classId");
        if (StringUtils.isBlank(dateDay) && StringUtils.isBlank(classId)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dateDay", dateDay);
        map.put("classId", Long.valueOf(classId));
        Page<TeacherIncreaseDetail> page = dateIncreaseUserService.queryTeacherIncreaseUserDetail(map, (pageNo - 1) * pageSize, pageSize, SqlOrderEnum.ASC.getAction());
        if (null == page || page.getCount() == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        } else {
            return page;
        }
    }


}
