package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.domain.*;
import cn.thinkjoy.jx.statistics.service.*;
import cn.thinkjoy.jx.statistics.util.Constants;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wangyongqiang on 15/9/21
 *
 *公告统计
 *
 */
@Controller
@RequestMapping("/statistics/notice")
public class NoticeStatisticsController {

    private static Logger LOGGER = LoggerFactory.getLogger(NoticeStatisticsController.class);




    @Autowired
    private IClassNoticeService classNoticeService;

    @Autowired
    private IUserNoticeService userNoticeService;




    /**
     * 获取发送公告的用户数折线图
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryLineChartSendNoticeUserNum",method = RequestMethod.GET)
    public List<ClassNotice> queryLineChartSendNoticeUserNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        String dateStart=request.getParameter("dateStart");
        String dateEnd=request.getParameter("dateEnd");
        if(StringUtils.isBlank(dateStart) && StringUtils.isBlank(dateEnd)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        List<Long> list=userPojo.getAreaIds();

        if(list == null || list.size() == 0 || (list !=null && list.size()==1 && list.get(0)==0)){//判断是否是公司管理员
            dataMap.put("areaIds",null);
        }else{
            dataMap.put("areaIds",list);
        }


        List<Long> schoolIds = userPojo.getSchoolIds();

        if(schoolIds == null || schoolIds.size() == 0 || (schoolIds !=null && schoolIds.size()==1 && schoolIds.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",schoolIds);
        }




        dataMap.put("dateStart",dateStart);
        dataMap.put("dateEnd", dateEnd);
        List<ClassNotice> classNoticeList = classNoticeService.queryByTime(dataMap);
        if(classNoticeList!=null && classNoticeList.size()>0){
            return  classNoticeList;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 获取通过时间点查询按区域分组发送公告的用户数
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAreaSendNoticeUserNum",method = RequestMethod.GET)
    public Page<ClassNotice> queryAreaSendNoticeUserNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay=request.getParameter("dateDay");
        if(StringUtils.isBlank(dateDay)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        List<Long> list=userPojo.getAreaIds();

        if(list == null || list.size() == 0 || (list !=null && list.size()==1 && list.get(0)==0)){//判断是否是公司管理员
            dataMap.put("areaIds",null);
        }else{
            dataMap.put("areaIds",list);
        }


        List<Long> schoolIds = userPojo.getSchoolIds();


        if(schoolIds == null || schoolIds.size() == 0 || (schoolIds !=null && schoolIds.size()==1 && schoolIds.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",schoolIds);
        }




        dataMap.put("dateDay", dateDay);
        Page<ClassNotice> page = classNoticeService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize, "areaId", SqlOrderEnum.ASC.getAction(), Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }
    /**
     * 根据区域Id获取按学校分组发送公告的用户数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "querySchoolSendNoticeUserNum",method = RequestMethod.GET)
    public Page<ClassNotice> querySchoolSendNoticeUserNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay=request.getParameter("dateDay");
        String areaId=request.getParameter("areaId");
        if(StringUtils.isBlank(dateDay) || StringUtils.isBlank(areaId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        List<Long> list=userPojo.getSchoolIds();


        if(list == null || list.size() == 0 || (list !=null && list.size()==1 && list.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",list);
        }

        dataMap.put("dateDay",dateDay);
        dataMap.put("areaId", Long.valueOf(areaId));
        Page<ClassNotice> page = classNoticeService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize, "schoolId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_AREAID_QUERY_SCHOOL_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 根据学校Id获取按班级分组发送公告的用户数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryClassSendNoticeUserNum",method = RequestMethod.GET)
    public Page<ClassNotice> queryClassSendNoticeUserNum(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay=request.getParameter("dateDay");
        String schoolId=request.getParameter("schoolId");
        if(StringUtils.isBlank(dateDay) && StringUtils.isBlank(schoolId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("dateDay",dateDay);
        dataMap.put("schoolId",  Long.valueOf(schoolId));
        Page<ClassNotice> page = classNoticeService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize, "classId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_SCHOOLID_QUERY_CLASS_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }



    /**
     * 获取用户发送的公告数折线图
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryLineChartUserSendNoticeNum",method = RequestMethod.GET)
    public List<ClassNotice> queryLineChartUserSendNoticeNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        String dateStart=request.getParameter("dateStart");
        String dateEnd=request.getParameter("dateEnd");
        if(StringUtils.isBlank(dateStart) && StringUtils.isBlank(dateEnd)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        List<Long> list=userPojo.getAreaIds();


        if(list == null || list.size() == 0 || (list !=null && list.size()==1 && list.get(0)==0)){//判断是否是公司管理员
            dataMap.put("areaIds",null);
        }else{
            dataMap.put("areaIds",list);
        }


        List<Long> schoolIds = userPojo.getSchoolIds();

        if(schoolIds == null || schoolIds.size() == 0 || (schoolIds !=null && schoolIds.size()==1 && schoolIds.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",schoolIds);
        }




        dataMap.put("dateStart",dateStart);
        dataMap.put("dateEnd", dateEnd);
        List<ClassNotice> classNoticeList = userNoticeService.queryByTime(dataMap);
        if(classNoticeList!=null && classNoticeList.size()>0){
            return  classNoticeList;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 获取通过时间点查询按区域分组用户发送的公告数
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAreaUserSendNoticeNum",method = RequestMethod.GET)
    public Page<ClassNotice> queryAreaUserSendNoticeNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay=request.getParameter("dateDay");
        if(StringUtils.isBlank(dateDay)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        List<Long> list=userPojo.getAreaIds();

        if(list == null || list.size() == 0 || (list !=null && list.size()==1 && list.get(0)==0)){//判断是否是公司管理员
            dataMap.put("areaIds",null);
        }else{
            dataMap.put("areaIds",list);
        }



        List<Long> schoolIds = userPojo.getSchoolIds();

        if(schoolIds == null || schoolIds.size() == 0 || (schoolIds !=null && schoolIds.size()==1 && schoolIds.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",schoolIds);
        }

        dataMap.put("dateDay", dateDay);
        Page<ClassNotice> page = userNoticeService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize, "areaId", SqlOrderEnum.ASC.getAction(), Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }
    /**
     * 根据区域Id获取按学校分组用户发送的公告数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "querySchoolUserSendNoticeNum",method = RequestMethod.GET)
    public Page<ClassNotice> querySchoolUserSendNoticeNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay=request.getParameter("dateDay");
        String areaId=request.getParameter("areaId");
        if(StringUtils.isBlank(dateDay) && StringUtils.isBlank(areaId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        List<Long> list=userPojo.getSchoolIds();

        if(list == null || list.size() == 0 || (list !=null && list.size()==1 && list.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",list);
        }

        dataMap.put("dateDay",dateDay);
        dataMap.put("areaId", Long.valueOf(areaId));
        Page<ClassNotice> page = userNoticeService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize, "schoolId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_AREAID_QUERY_SCHOOL_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 根据学校Id获取按班级分组用户发送的公告数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryClassUserSendNoticeNum",method = RequestMethod.GET)
    public Page<ClassNotice> queryClassUserSendNoticeNum(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay=request.getParameter("dateDay");
        String schoolId=request.getParameter("schoolId");
        if(StringUtils.isBlank(dateDay) && StringUtils.isBlank(schoolId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("dateDay",dateDay);
        dataMap.put("schoolId",  Long.valueOf(schoolId));
        Page<ClassNotice> page = userNoticeService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize, "classId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_SCHOOLID_QUERY_CLASS_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }


}
