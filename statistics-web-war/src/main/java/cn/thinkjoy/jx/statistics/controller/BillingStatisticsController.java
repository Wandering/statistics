package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.ExcelDataHandle.Excel2007Reader;
import cn.thinkjoy.jx.statistics.ExcelDataHandle.IRowReader;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.domain.Billing;
import cn.thinkjoy.jx.statistics.domain.BillingInfo;
import cn.thinkjoy.jx.statistics.domain.ClassDayBilling;
import cn.thinkjoy.jx.statistics.service.IBillingInfoService;
import cn.thinkjoy.jx.statistics.service.IBillingService;
import cn.thinkjoy.jx.statistics.service.IClassDayBillingService;
import cn.thinkjoy.jx.statistics.util.Constants;
import cn.thinkjoy.jx.statistics.util.ExcelUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@RequestMapping("/statistics/billing")
public class BillingStatisticsController {

    private static Logger LOGGER = LoggerFactory.getLogger(BillingStatisticsController.class);


    @Autowired
    @Qualifier("arrearageImportRowReader")
    private IRowReader rowReader;

    @Autowired
    private IBillingService billingService;

    @Autowired
    private IBillingInfoService billingInfoService;


    @Autowired
    private IClassDayBillingService classDayBillingService;

    /**
     * 获取按区域分组的实时欠费人数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryAreaBillingNum",method = RequestMethod.GET)
    public Page<Billing> queryAreaBillingNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        Map<String,Object> map=new HashMap<>();
        List<Long> list=userPojo.getAreaIds();



/*        if(list!=null && list.size()==1 && list.get(0)==-1){//判断是否是公司管理员
            map.put("areaIds",null);
        }else{
            map.put("areaIds",list);
        }*/


        if(list == null || list.size() == 0 || (list !=null && list.size()==1 && list.get(0)==0)){//判断是否是公司管理员
            map.put("areaIds",null);
        }else{
            map.put("areaIds",list);
        }




        List<Long> schoolIds = userPojo.getSchoolIds();

        if(schoolIds == null || schoolIds.size() == 0 || (schoolIds !=null && schoolIds.size()==1 && schoolIds.get(0)==0)) {
            map.put("schoolIds",null);
        } else {
            map.put("schoolIds",schoolIds);
        }


        Page<Billing> page = billingService.queryByPage(map,(currentPageNo - 1) * pageSize, pageSize,"areaId", SqlOrderEnum.ASC.getAction(),Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 根据areaId获取按学校分组的实时欠费人数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "querySchoolBillingNum",method = RequestMethod.GET)
    public Page<Billing> querySchoolBillingNum(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        if(userPojo==null){
            throw  new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        }
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String areaId=request.getParameter("areaId");
        if(StringUtils.isBlank(areaId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("areaId", Long.valueOf(areaId));
        List<Long> list=userPojo.getSchoolIds();



        if(list == null || list.size() == 0 || (list !=null && list.size()==1 && list.get(0)==0)) {
            dataMap.put("schoolIds",null);
        } else {
            dataMap.put("schoolIds",list);
        }


        Page<Billing> page = billingService.queryByPage(dataMap,(currentPageNo - 1) * pageSize, pageSize,"areaId", SqlOrderEnum.ASC.getAction(),Constants.WHERR_AREAID_QUERY_SCHOOL_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }
    /**
     * 根据schoolId获取按学校分组的实时欠费人数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryClassBillingNum",method = RequestMethod.GET)
    public Page<Billing> queryClassBillingNum(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String schoolId=request.getParameter("schoolId");
        if(StringUtils.isBlank(schoolId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("schoolId", Long.valueOf(schoolId));
        Page<Billing> page = billingService.queryByPage(dataMap,(currentPageNo - 1) * pageSize, pageSize,"areaId", SqlOrderEnum.ASC.getAction(),Constants.WHERR_SCHOOLID_QUERY_CLASS_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }
    /**
     * 获取实时欠费详情
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryRealTimeBillingDetail",method = RequestMethod.GET)
    public Page<BillingInfo> queryRealTimeBillingDetail(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String classId=request.getParameter("classId");
        Map<String,Object> dataMap=new HashMap<>();
        if(!StringUtils.isBlank(classId)){
            dataMap.put("classId",Long.valueOf(classId));
        }






        Page<BillingInfo> page = billingInfoService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize,"classId", SqlOrderEnum.ASC.getAction());
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 获取历史欠费折线图
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryLineChartHistoryBilling",method = RequestMethod.GET)
    public List<ClassDayBilling> queryLineChartHistoryBilling(HttpServletRequest request) {
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
/*
        if(list!=null && list.size()==1 && list.get(0)==-1){//判断是否是公司管理员
            dataMap.put("areaIds",null);
        }else{
            dataMap.put("areaIds",list);
        }
*/


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
        List<ClassDayBilling> classDayBillingList = classDayBillingService.queryByTime(dataMap);
        if(classDayBillingList!=null && classDayBillingList.size()>0){
            return  classDayBillingList;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 获取通过时间点查询按区域分组的历史欠费数
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAreaHistoryBillingNum",method = RequestMethod.GET)
    public Page<ClassDayBilling> queryAreaHistoryBillingNum(HttpServletRequest request) {
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
        dataMap.put("dateDay", dateDay);
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



        Page<ClassDayBilling> page = classDayBillingService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize, "areaId", SqlOrderEnum.ASC.getAction(), Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }
    /**
     * 根据区域Id获取按学校分组的历史欠费数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "querySchoolHistoryBillingNum",method = RequestMethod.GET)
    public Page<ClassDayBilling> querySchoolHistoryBillingNum(HttpServletRequest request) {
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
        dataMap.put("dateDay",dateDay);
        dataMap.put("areaId", Long.valueOf(areaId));
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


        Page<ClassDayBilling> page = classDayBillingService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize, "schoolId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_AREAID_QUERY_SCHOOL_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 根据学校Id获取按班级分组的历史欠费数
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryClassHistoryBillingNum",method = RequestMethod.GET)
    public Page<ClassDayBilling> queryClassHistoryBillingNum(HttpServletRequest request) {
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
        Page<ClassDayBilling> page = classDayBillingService.queryByPage(dataMap, (currentPageNo - 1) * pageSize, pageSize, "classId", SqlOrderEnum.ASC.getAction(), Constants.WHERR_SCHOOLID_QUERY_CLASS_NUM);
        if(page == null || page.getCount()==0 ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    /**
     * 上传欠费数据excel表，先将需要数据保存后，再执行sql语句修改更新相关字段
     * @author 苏州 20151217 13：00
     * @param request request
     */
    @ResponseBody
    @RequestMapping(value = "uploadFile",method = RequestMethod.POST)
    public ModelMap uploadFile(HttpServletRequest request){

        ModelMap mmp = new ModelMap();
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest)request;
        MultipartFile mf = mulRequest.getFile("fileUpload");


        if(mf == null){
            mmp.addAttribute("msg","文件上传失败");
            return mmp;
        }
        String suffix = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
        if(!".xlsx".equalsIgnoreCase(suffix)) {
            mmp.addAttribute("msg","文件格式不正确，必须为Excel2007-2010文件");
            return mmp;
        }

        //删除历史数据
        billingInfoService.deleteHistoryData();

        Excel2007Reader excel07 = new Excel2007Reader();
        excel07.setRowReader(rowReader);
        try {
            excel07.process(mf.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //更新 userId 字段
        int i = billingInfoService.updateUserId();

        //删除无效用户 （账户id不存在）
        i = billingInfoService.deleteInvalidUserId();

        //根据新插入数据更新其余字段
        i = billingInfoService.updateImportBatch();

        //删除旧数据
        i = billingInfoService.deleteOldBillingInfo();

        //删除payNum和phoneNumb不一致数据
        i = billingInfoService.deleteDifferentNumber();

        // 并将新导入数据，新旧标示更改为旧数据
        i = billingInfoService.updateNewBillingInfo();

        //清空统计数据
        i = billingInfoService.deleteBillingStatistics();

        //填入统计数据
        i = billingInfoService.updateBillingStatistics();

        mmp.addAttribute("msg","欠费文件导入成功");
        return mmp;
    }



}
