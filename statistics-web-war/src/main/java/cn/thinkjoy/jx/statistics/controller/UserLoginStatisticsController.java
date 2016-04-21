package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IClassDayLoginService;
import cn.thinkjoy.jx.statistics.service.IUserDayLoginService;
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
import java.text.ParseException;
import java.util.*;


/**
 * Created by wangyongqiang on 15/9/21
 *
 *群聊天统计
 *
 */
@Controller
@RequestMapping("/statistics/userLogin")
public class UserLoginStatisticsController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserLoginStatisticsController.class);




    @Autowired
    private IClassDayLoginService classDayLoginService;

    @Autowired
    private IUserDayLoginService  userDayLoginService;


    /**
     * 获取日班级登录用户数折线图
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryLoginUserNum",method = RequestMethod.GET)
    public List<StatisticsPojo> queryLoginUserNum(HttpServletRequest request) {
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
/*        if(list!=null && list.size()==1 && list.get(0)==-1){//判断是否是公司管理员
            dataMap.put("areaIds",null);
        }else{
            dataMap.put("areaIds",list);
        }*/


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
        List<StatisticsPojo> statisticsPojoList = classDayLoginService.queryByTime(dataMap);
        if(statisticsPojoList!=null && statisticsPojoList.size()>0){
            return  statisticsPojoList;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }


    /**
     * 根据时间点获取按区域分组的登录用户教师和家长数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryAreaLoginUserNum",method = RequestMethod.GET)
    public Page<StatisticsPojo> queryAreaLoginUserNum(HttpServletRequest request) {
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
/*        if(list!=null && list.size()==1 && list.get(0)==-1){//判断是否是公司管理员
            dataMap.put("areaIds",null);
        }else{
            dataMap.put("areaIds",list);
        }*/


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



        dataMap.put("dateDay",dateDay);
        Page<StatisticsPojo> page = classDayLoginService.queryByPageTime(dataMap, (currentPageNo - 1) * pageSize, pageSize, "areaId", SqlOrderEnum.ASC.getAction(), Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }
    /**
     * 根据区域Id获取按学校分组的登录用户教师和家长数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "querySchoolLoginUserNum",method = RequestMethod.GET)
    public Page<StatisticsPojo> querySchoolLoginUserNum(HttpServletRequest request) {
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
/*        if(list!=null && list.size()==1 && list.get(0)==-1){//判断是否是公司管理员
            dataMap.put("schoolIds",null);
        }else{
            dataMap.put("schoolIds",list);
        }*/


        if(list == null || list.size() == 0 || (list !=null && list.size()==1 && list.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",list);
        }



        dataMap.put("dateDay",dateDay);
        dataMap.put("areaId", Long.valueOf(areaId));
        Page<StatisticsPojo> page = classDayLoginService.queryByPageTime(dataMap, (currentPageNo - 1) * pageSize, pageSize, "schoolId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_AREAID_QUERY_SCHOOL_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 根据学校Id获取按班级分组的登录用户教师和家长数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryClassLoginUserNum",method = RequestMethod.GET)
    public Page<StatisticsPojo> queryClassLoginUserNum(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dateDay=request.getParameter("dateDay");
        String schoolId=request.getParameter("schoolId");
        if(StringUtils.isBlank(dateDay) && StringUtils.isBlank(schoolId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("dateDay",dateDay);
        dataMap.put("schoolId", Long.valueOf(schoolId));
        Page<StatisticsPojo> page = classDayLoginService.queryByPageTime(dataMap, (currentPageNo - 1) * pageSize, pageSize, "classId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_SCHOOLID_QUERY_CLASS_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }


   /**************************************/
    /**
     * 获取日班级用户登录次数折线图
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryUserLoginNum",method = RequestMethod.GET)
    public List<StatisticsPojo> queryUserLoginNum(HttpServletRequest request) {
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
        List<StatisticsPojo> statisticsPojoList = userDayLoginService.queryByTime(dataMap);
        if(statisticsPojoList!=null && statisticsPojoList.size()>0){
            return  statisticsPojoList;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 根据时间点获取按区域分组的家长和教师的登录次数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryAreaUserLoginNum",method = RequestMethod.GET)
    public Page<StatisticsPojo> queryAreaUserLoginNum(HttpServletRequest request) {
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
        Page<StatisticsPojo> page = userDayLoginService.queryByPageTime(dataMap, (currentPageNo - 1) * pageSize, pageSize, "areaId", SqlOrderEnum.ASC.getAction(), Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }
    /**
     * 根据区域Id获取按学校分组的家长和教师的登录次数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "querySchoolUserLoginNum",method = RequestMethod.GET)
    public Page<StatisticsPojo> querySchoolUserLoginNum(HttpServletRequest request) {
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
        Page<StatisticsPojo> page = userDayLoginService.queryByPageTime(dataMap, (currentPageNo - 1) * pageSize, pageSize, "schoolId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_AREAID_QUERY_SCHOOL_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 根据学校Id获取按班级分组的家长和教师的登录次数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryClassUserLoginNum",method = RequestMethod.GET)
    public Page<StatisticsPojo> queryClassUserLoginNum(HttpServletRequest request) {
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
        Page<StatisticsPojo> page = userDayLoginService.queryByPageTime(dataMap, (currentPageNo - 1) * pageSize, pageSize, "classId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_SCHOOLID_QUERY_CLASS_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**************************************/
    /**
     * 获取用户登录天数分部柱形图
     */
    @ResponseBody
    @RequestMapping(value = "queryUserLoginDateNum",method = RequestMethod.GET)
    public List<StatisticsPojo>  queryUserLoginDateNum(HttpServletRequest request) throws ParseException {



        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }

        Map<String,Object> dataMap=new HashMap<>();

        List<Long> areaIds=userPojo.getAreaIds();


        if(areaIds == null || areaIds.size() == 0 || (areaIds !=null && areaIds.size()==1 && areaIds.get(0)==0)){//判断是否是公司管理员
            dataMap.put("areaIds",null);
        }else{
            dataMap.put("areaIds",areaIds);
        }



        List<Long> schoolIds = userPojo.getSchoolIds();

        if(schoolIds == null || schoolIds.size() == 0 || (schoolIds !=null && schoolIds.size()==1 && schoolIds.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",schoolIds);
        }


        String dateDay = request.getParameter("dateDay");
         String range = request.getParameter("range");

        if (StringUtils.isBlank(dateDay)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
            List<StatisticsPojo> list = userDayLoginService.queryLoginUserCountByTime(dataMap,range,dateDay);
        if(list!=null && list.size()>0){

            return  list;

        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 根据时间点获取按区域分组的登录用户教师和家长数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryAreaUserLoginDateNum",method = RequestMethod.GET)
    public Page<StatisticsPojo> queryAreaUserLoginDateNum(HttpServletRequest request) throws ParseException {

        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }

        Map<String,Object> dataMap=new HashMap<>();


        List<Long> areaIds=userPojo.getAreaIds();


        if(areaIds == null || areaIds.size() == 0 || (areaIds !=null && areaIds.size()==1 && areaIds.get(0)==0)){//判断是否是公司管理员
            dataMap.put("areaIds",null);
        }else{
            dataMap.put("areaIds",areaIds);
        }


        List<Long> schoolIds = userPojo.getSchoolIds();


        if(schoolIds == null || schoolIds.size() == 0 || (schoolIds !=null && schoolIds.size()==1 && schoolIds.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",schoolIds);
        }


        String dateDay = request.getParameter("dateDay");
        if (StringUtils.isBlank(dateDay)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String start=request.getParameter("start");
        String end=request.getParameter("end");
        if (StringUtils.isBlank(start)&&StringUtils.isBlank(end)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Page<StatisticsPojo> page = userDayLoginService.queryLoginUserCountByPageTime(dataMap,dateDay,start,end,(currentPageNo - 1) * pageSize, pageSize, Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }
    /**
     * 根据区域Id获取按学校分组的家长和教师的登录次数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "querySchoolUserLoginDateNum",method = RequestMethod.GET)
    public Page<StatisticsPojo> querySchoolUserLoginDateNum(HttpServletRequest request) throws ParseException {
        String dateDay = request.getParameter("dateDay");
        if (StringUtils.isBlank(dateDay)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String start=request.getParameter("start");
        String end=request.getParameter("end");
        String areaId = request.getParameter("areaId");
        if (StringUtils.isBlank(start)&&StringUtils.isBlank(end)&&StringUtils.isBlank(areaId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }



        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }

        Map<String,Object> dataMap=new HashMap<>();


        List<Long> schoolIds = userPojo.getSchoolIds();

        if(schoolIds == null || schoolIds.size() == 0 || (schoolIds !=null && schoolIds.size()==1 && schoolIds.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",schoolIds);
        }


        Page<StatisticsPojo> page = userDayLoginService.queryLoginUserCountByPageTime(dataMap, dateDay,start, end, (currentPageNo - 1) * pageSize, pageSize,Constants.WHERR_AREAID_QUERY_SCHOOL_NUM,Integer.parseInt(areaId));
        if (page==null||page.getCount()==0){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 根据学校Id获取按班级分组的家长和教师的登录次数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryClassUserLoginDateNum",method = RequestMethod.GET)
    public Page<StatisticsPojo> queryClassUserLoginDateNum(HttpServletRequest request) throws ParseException {
        String dateDay = request.getParameter("dateDay");
        if (StringUtils.isBlank(dateDay)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String start=request.getParameter("start");
        String end=request.getParameter("end");
        String schoolId = request.getParameter("schoolId");
        if (StringUtils.isBlank(start)&&StringUtils.isBlank(end)&&StringUtils.isBlank(schoolId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }






        //在方法中增加了一个空map集合
        Page<StatisticsPojo> page = userDayLoginService.queryLoginUserCountByPageTime(new HashMap<String, Object>() ,dateDay,start,end, (currentPageNo - 1) * pageSize, pageSize,Constants.WHERR_SCHOOLID_QUERY_CLASS_NUM,Integer.parseInt(schoolId));
        if (page==null||page.getCount()==0){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }
}
