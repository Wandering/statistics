package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.CSVUtil;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import cn.thinkjoy.jx.statistics.domain.*;
import cn.thinkjoy.jx.statistics.service.IArrearAnalysisService;
import cn.thinkjoy.jx.statistics.util.DownloadUtil;
import cn.thinkjoy.jx.statistics.util.ExcelUtil;
import com.jlusoft.microschool.core.utils.PhoneUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by Mike on 2015/12/15.
 */
@Controller
@RequestMapping("/statistics/arrearData")
public class ArrearDataAnalysisController {

    @Autowired
    IArrearAnalysisService arrearAnalysisService;
    @Autowired
    private static Logger LOGGER = LoggerFactory.getLogger(ArrearDataAnalysisController.class);

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFile(HttpServletRequest request) throws IOException {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mulRequest.getFile("fileUpload");
        String fileName = file.getOriginalFilename();
        Map map = new HashMap();
        if (file == null) {
            map.put("msg", "文件上传失败");
            return map;
        }
        if (!".csv".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(".")))) {
            map.put("msg", "文件格式不正确，必须为CSV格式的文件");
            return map;
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        TemplateInfo templateInfo = new TemplateInfo();
        templateInfo.setImportDate(new Date().getTime());
        templateInfo.setTemplateName(fileName.substring(0, fileName.lastIndexOf(".")));
        templateInfo.setAccountId(userPojo.getAccountId());
        templateInfo.setStatus(1);
        fileName = "aaaa" + fileName.substring(fileName.lastIndexOf("."));
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        } else {
            targetFile.delete();
        }
        file.transferTo(targetFile);
        arrearAnalysisService.saveAllData(path + File.separator + fileName);
        arrearAnalysisService.saveTemplateInfo(templateInfo);
        int templateId = templateInfo.getTemplateId();
        try {
            long rowNum = arrearAnalysisService.countRowNum();
            if (rowNum > 0) {
                for (int row = 0; row < rowNum; row += 3000) {
                    List<OriginalArrearTemplate> list = arrearAnalysisService.getOriginalArrearTemplate(row);
                    saveArrearInfo(list, templateId);
                }
            }
            Map mapParam = new HashMap();
            arrearAnalysisService.saveOutUserInfo(templateId);
            arrearAnalysisService.savePageUserInfo(templateId);
            mapParam.put("templateId", templateId);
            mapParam.put("status", 0);
            if (arrearAnalysisService.countErrorData(templateId) > 0) {
                mapParam.put("errorData", 1);
            } else {
                mapParam.put("errorData", 0);
            }
            arrearAnalysisService.updateTemplateInfo(mapParam);
            map.put("msg", "欠费数据导入成功");
        }catch (Exception e){
            map.put("msg", "由于数据原因导致数分析失败，请检查数据后重新上传!");
            e.printStackTrace();
        }finally {
            arrearAnalysisService.deleteTempTableData();
        }

        return map;
    }

    /*
     * 导入处理好的数据（临时）
     */
    @RequestMapping(value = "uploadFile1", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFile1(HttpServletRequest request) throws IOException {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mulRequest.getFile("fileUpload");
        String fileName = file.getOriginalFilename();
        Map map = new HashMap();
        if (file == null) {
            map.put("msg", "文件上传失败");
            return map;
        }
        if (!".csv".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(".")))) {
            map.put("msg", "文件格式不正确，必须为CSV格式的文件");
            return map;
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        TemplateInfo templateInfo = new TemplateInfo();
        templateInfo.setImportDate(new Date().getTime());
        templateInfo.setTemplateName(fileName.substring(0, fileName.lastIndexOf(".")));
        templateInfo.setAccountId(userPojo.getAccountId());
        templateInfo.setStatus(1);
        fileName = "aaaa" + fileName.substring(fileName.lastIndexOf("."));
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        } else {
            targetFile.delete();
        }
        file.transferTo(targetFile);
        arrearAnalysisService.saveDealData(path + File.separator + fileName);
        arrearAnalysisService.saveTemplateInfo(templateInfo);
        int templateId = templateInfo.getTemplateId();
        arrearAnalysisService.updateTemplateId(templateId);
        try {

            Map mapParam = new HashMap();
            arrearAnalysisService.saveOutUserInfo(templateId);
            arrearAnalysisService.savePageUserInfo(templateId);
            mapParam.put("templateId", templateId);
            mapParam.put("status", 0);
            if (arrearAnalysisService.countErrorData(templateId) > 0) {
                mapParam.put("errorData", 1);
            } else {
                mapParam.put("errorData", 0);
            }
            arrearAnalysisService.updateTemplateInfo(mapParam);
            map.put("msg", "欠费数据导入成功");
        }catch (Exception e){
            map.put("msg", "由于数据原因导致数分析失败，请检查数据后重新上传!");
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 获取欠费数据导入历史记录
     *
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryArrearImportHistory", method = RequestMethod.GET)
    public Page<TemplateInfo> queryArrearImportHistory(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String fileName = request.getParameter("fileName");
        String uploadDate = request.getParameter("dateDay");
        Map<String, Object> map = new HashMap<String, Object>();
        if (fileName != null && !fileName.equals("")) {
            fileName = new StringBuilder("'%").append(fileName).append("%'").toString();
            map.put("fileName", fileName);
        }
        if (uploadDate != null && !uploadDate.equals("")) {
            map.put("dateDay", "'" + uploadDate + "'");
        }
        Page<TemplateInfo> page = arrearAnalysisService.getTemplateInfoHistory(map, (currentPageNo - 1) * pageSize, pageSize, "templateId", SqlOrderEnum.DESC.getAction());
        if (page == null || page.getCount() == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    @RequestMapping(value = "queryArrearInfoByPage", method = RequestMethod.GET)
    @ResponseBody
    public Page<ArrearInfo> queryArrearInfoByPage(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String dataType = request.getParameter("dataType");
        String areaId = request.getParameter("areaId");
        Map<String, Object> map = new HashMap<>();

        long templateId = Long.parseLong(request.getParameter("templateId"));
        map.put("templateId", templateId);
        if (areaId != null && !areaId.equals("")) {
            map.put("areaId", areaId);
        }
        if (dataType.equals("effective")) {
            map.put("effective", "有效");
        }
        Page<ArrearInfo> page = arrearAnalysisService.getArrearInfoByPage(map, currentPageNo, pageSize, "arrearAmount", SqlOrderEnum.DESC.getAction());

        return page;
    }

    /**
     * 欠费信息分析导出报表
     */

    @RequestMapping(value = "downloadReportForm")
    public void downloadTemp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("templateId");
        Long templateId = Long.valueOf(id);
        String fileNames = request.getParameter("fileName");
        String fileName = java.net.URLDecoder.decode(fileNames, "UTF-8");
        List<PageUserInfo> list = arrearAnalysisService.queryPageUser(templateId);
        ExcelUtil excel = new ExcelUtil();
        excel.createBookAndSheet(true, "sheet1");
        excel.writeRow(0, "区域", "用户总数", "收费用户数", "有效用户数");
        if (list != null && list.size() > 0) {
            int rowNum = 0;
            for (PageUserInfo pageUserInfo : list) {
                rowNum++;
                excel.writeRow(rowNum, pageUserInfo.getAreaName(), pageUserInfo.getUserTotalNumber(), pageUserInfo.getUserTollNumber(), pageUserInfo.getUserEffective());
            }
        }
        DownloadUtil.downloadXls(response, fileName + "报表", excel.getInputStream());
    }

    /**
     * 导出错误号码
     */
    @ResponseBody
    @RequestMapping(value = "downloadErrorNumber")
    public void downloadErrorNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("templateId");
        Long templateId = Long.valueOf(id);
        String fileNames = request.getParameter("fileName");
        String fileName = java.net.URLDecoder.decode(fileNames, "UTF-8");
        List<String> list = arrearAnalysisService.queryNumberByTemplateId(templateId);
        ExcelUtil excel = new ExcelUtil();
        excel.createBookAndSheet(false, "sheet1");
        excel.writeRow(0, "错误数据号码");
        if (list != null && list.size() > 0) {
            int rowNum = 0;
            for (String s : list) {
                rowNum++;
                excel.writeRow(rowNum, s);
            }
        }
        DownloadUtil.downloadXlsx(response, fileName + "错误信息报表", excel.getInputStream());
    }

    /**
     * 对比分析导出excel
     */

    @ResponseBody
    @RequestMapping(value = "downloadAnalysisExcel")
    public void downloadAnalysisExcel(HttpServletRequest request, HttpServletResponse response) {
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        String templateId1 = request.getParameter("templateId1");
        Long tempId1 = Long.valueOf(templateId1);
        String templateId2 = request.getParameter("templateId2");
        Long tempId2 = Long.valueOf(templateId2);
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        List<Long> areaIds = userPojo.getAreaIds();
        List<Map<String, Object>> list = comparData(tempId1, tempId2, areaIds);
        ExcelUtil excel = new ExcelUtil();
        excel.createBookAndSheet(true, "sheet1");
        excel.writeRow(0, "区域", dateStart + "用户数", dateEnd + "用户数", "净增数", dateStart + "有效数", dateEnd + "有效数", "净增有效数");
        if (list != null && list.size() > 0) {
            int rowNum = 0;
            for (Map<String, Object> map : list) {
                rowNum++;
                excel.writeRow(rowNum, map.get("areaName"), map.get("dateStartTollNo"), map.get("dateEndTollNo"), map.get("addTollNo"), map.get("dateStartEffective"), map.get("dateEndEffective"), map.get("addEffective"));
            }
        }
        DownloadUtil.downloadXls(response, dateStart + "至" + dateEnd + "的报表", excel.getInputStream());
    }


    /**
     * 欠费信息对比
     *
     * @param request(dateStart,dateEnd(精确到天))
     * @author suzhou 20151223 15:34
     */
    @ResponseBody
    @RequestMapping(value = "arrearComparData", method = RequestMethod.GET)
    public Map arrearComparData(HttpServletRequest request) {
        Map mm = new HashMap();
        String date1 = request.getParameter("dateStart");
        String date2 = request.getParameter("dateEnd");
        if (date1 == null || date2 == null || "".equals(date1) || "".equals(date2)) {
            //第一次进入或者没有选择时间不查询
            mm.put("list", null);
            return mm;
        }

        //根据时间判断数据是否存在
        Long id1 = arrearAnalysisService.checkDate(date1);
        if (id1 == null || id1 == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE_F.getCode(), ERRORCODE.NO_MESSAGE_F.getMessage());
        }
        Long id2 = arrearAnalysisService.checkDate(date2);
        if (id2 == null || id2 == 0) {
            throw new BizException(ERRORCODE.NO_MESSAGE_S.getCode(), ERRORCODE.NO_MESSAGE_S.getMessage());
        }
        mm.put("templateId1", id1);
        mm.put("templateId2", id2);
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        List<Long> areaIds = userPojo.getAreaIds();
/*
        if (areaIds == null || areaIds.size() == 0) {
            throw new BizException(ERRORCODE.NO_PERMISSION.getCode(), ERRORCODE.NO_PERMISSION.getMessage());
        }*/
        mm.put("list", comparData(id1, id2, areaIds));
        return mm;
    }

    /*
     *用户详情导出
     */
    @RequestMapping(value = "exportArrearDatail", method = RequestMethod.GET)
    public void exportArrearDatail(HttpServletRequest request, HttpServletResponse response){
        String templateId = request.getParameter("templateId");
        String fileNames = request.getParameter("fileName");
        String fileName = null;
        try {
            fileName = java.net.URLDecoder.decode(fileNames, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String areaId = request.getParameter("areaId");
        String dataType = request.getParameter("dataType");
        Map<String, Object> map = new HashMap<>();
        map.put("templateId", templateId);
        if (areaId != null && !areaId.equals("")) {
            map.put("areaId", areaId);
        }
        if ("effective".equals(dataType)) {
            map.put("effective", "'有效'");
        }
        StringBuilder csvFileData = new StringBuilder("查询号码,设备状态,商品名称,欠费账龄,欠费金额,计费类型,计费标签,家长姓名,区域名称,学校名称,班级名称,学生姓名,计费号码,登录账号");
        csvFileData.append("\r");
        int count = arrearAnalysisService.countUserNum(map);
        if (count > 0) {
            Page<ArrearInfo> page = arrearAnalysisService.getArrearInfoByPage(map, 0, count, "arrearAmount", SqlOrderEnum.DESC.getAction());
            List  list = page.getList();
            int i = 1;
            for (; i < list.size(); i++) {
                ArrearInfo  arrearInfo = (ArrearInfo) list.get(i);
                if (arrearInfo == null) {
                    System.out.println("null");
                    break;
                }
         /*       System.out.println("第"+i+"条数据");
                if(arrearInfo.getPhoneNumber().equals("18092488669"))
                {
                    System.out.println("第"+i+"条数据");
                }*/
                String dataUser = (CSVUtil.getCSVDataStr(arrearInfo.getPhoneNumber()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getDeviceStatus()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getGoodsName()))+
                        ","+(arrearInfo.getArrearAge())+
                        ","+(arrearInfo.getArrearAmount())+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getBillingType()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getBillingLabel()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getUserName()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getAreaName()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getShoolName()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getClassName()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getStudentName()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getPayNumber()))+
                        ","+(CSVUtil.getCSVDataStr(arrearInfo.getLoginNumber()));
                if(dataUser.contains("\r"))
                {
                    csvFileData.append(dataUser);
                }else
                {
                    csvFileData.append(dataUser).append("\r");
                }




            }
        }
        try {
            String tempPath = request.getSession().getServletContext().getRealPath("upload")+"\\";
            File file = new File(tempPath+fileName+"用户信息.csv");
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(csvFileData.toString());
            ps.close();
            CSVUtil.exportFile(response,tempPath,fileName+"用户信息");
            CSVUtil.deleteFiles(tempPath+fileName+"用户信息.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法为平台消息对比的排序方法   suzhou  20151228  15：00
     */
    private List comparData(Long id1, Long id2, List<Long> areaIds) {
        List<Map<String, Map>> list = new ArrayList<Map<String, Map>>();
        //根据导入记录ID查询具体对比参数，并取得相应权限内区域
        List<PageUserInfo> pageUserInfo1 = checkUser(arrearAnalysisService.queryPageUser(id1), areaIds);
        List<PageUserInfo> pageUserInfo2 = checkUser(arrearAnalysisService.queryPageUser(id2), areaIds);

        //将查询后区域相同的值，放在同一行
        Map<String, Map> userInfos = new HashMap<String, Map>();

        //排序后的集合
        if (pageUserInfo1 != null && pageUserInfo2 != null && pageUserInfo1.size() == 1 && pageUserInfo2.size() == 1) {
            Map<String, Object> map = new HashMap<String, Object>();
            PageUserInfo pu1 = pageUserInfo1.get(0);
            PageUserInfo pu2 = pageUserInfo2.get(0);
            map.put("dateStartTollNo", pu1.getUserTotalNumber());//用户总数
            map.put("dateEndTollNo", pu2.getUserTotalNumber());
            map.put("addTollNo", pu2.getUserTotalNumber() - pu1.getUserTotalNumber());//平台净增数
            map.put("dateStartEffective", pu1.getUserEffective());//有效用户数
            map.put("dateEndEffective", pu2.getUserEffective());
            map.put("addEffective", pu2.getUserEffective() - pu1.getUserEffective());//平台有效净增数
            map.put("areaName", pu1.getAreaName());
            map.put("areaId", pu1.getAreaId());
            userInfos.put(pageUserInfo1.get(0).getAreaName(), map);
            return list;
        }

        for (int i = 0; i < pageUserInfo1.size(); i++) {
            PageUserInfo pu1 = pageUserInfo1.get(i);
            for (int j = 0; j < pageUserInfo1.size(); j++) {
                PageUserInfo pu2 = pageUserInfo2.get(j);
                if (pu1 != null && pu2 != null && pu1.getAreaName().equals(pu2.getAreaName())) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("dateStartTollNo", pu1.getUserTotalNumber());//用户总数
                    map.put("dateEndTollNo", pu2.getUserTotalNumber());
                    map.put("addTollNo", pu2.getUserTotalNumber() - pu1.getUserTotalNumber());//平台净增数
                    map.put("dateStartEffective", pu1.getUserEffective());//有效用户数
                    map.put("dateEndEffective", pu2.getUserEffective());
                    map.put("addEffective", pu2.getUserEffective() - pu1.getUserEffective());//平台有效净增数
                    map.put("areaName", pu1.getAreaName());
                    map.put("areaId", pu1.getAreaId());
                    userInfos.put(pu1.getAreaName(), map);
                }
            }
        }

        if (userInfos.get("灞桥分部") != null) {
            list.add(userInfos.get("灞桥分部"));
        }
        if (userInfos.get("碑林分部") != null) {
            list.add(userInfos.get("碑林分部"));
        }
        if (userInfos.get("沣东分部") != null) {
            list.add(userInfos.get("沣东分部"));
        }
        if (userInfos.get("莲湖分部") != null) {
            list.add(userInfos.get("莲湖分部"));
        }
        if (userInfos.get("未央分部") != null) {
            list.add(userInfos.get("未央分部"));
        }
        if (userInfos.get("新城分部") != null) {
            list.add(userInfos.get("新城分部"));
        }
        if (userInfos.get("雁塔分部") != null) {
            list.add(userInfos.get("雁塔分部"));
        }
        if (userInfos.get("市区合计") != null) {
            list.add(userInfos.get("市区合计"));
        }
        if (userInfos.get("高陵分公司") != null) {
            list.add(userInfos.get("高陵分公司"));
        }
        if (userInfos.get("航天分公司") != null) {
            list.add(userInfos.get("航天分公司"));
        }
        if (userInfos.get("户县分公司") != null) {
            list.add(userInfos.get("户县分公司"));
        }
        if (userInfos.get("蓝田分公司") != null) {
            list.add(userInfos.get("蓝田分公司"));
        }
        if (userInfos.get("临潼分公司") != null) {
            list.add(userInfos.get("临潼分公司"));
        }
        if (userInfos.get("阎良分公司") != null) {
            list.add(userInfos.get("阎良分公司"));
        }
        if (userInfos.get("杨凌分公司") != null) {
            list.add(userInfos.get("杨凌分公司"));
        }
        if (userInfos.get("长安分公司") != null) {
            list.add(userInfos.get("长安分公司"));
        }
        if (userInfos.get("周至分公司") != null) {
            list.add(userInfos.get("周至分公司"));
        }
        if (userInfos.get("郊县合计") != null) {
            list.add(userInfos.get("郊县合计"));
        }
        if (userInfos.get("汇总") != null) {
            list.add(userInfos.get("汇总"));
        }
        return list;
    }


    //根据当前登录用户区域ID集合，匹配所能查看区域
    //如果有查看16个以上区县的权利，则不用对比
    private List<PageUserInfo> checkUser(List<PageUserInfo> list, List<Long> areaIds) {
        if (areaIds == null) {
            return list;
        }
        List<PageUserInfo> checkU = new ArrayList<PageUserInfo>();
        for (int i = 0; i < list.size(); i++) {
            PageUserInfo pu = list.get(i);
            for (int j = 0; j < areaIds.size(); j++) {
                if (pu.getAreaId() == areaIds.get(j)) {
                    checkU.add(pu);
                }
            }
        }
        return checkU;
    }


    /*
     * 欠费信息处理并保存数据库
     */
    private void saveArrearInfo(List<OriginalArrearTemplate> list, long templateId) {
        List<ArrearInfo> arrearInfoList = new ArrayList<ArrearInfo>();
        for (OriginalArrearTemplate originalArrearTemplate : list) {
            String goodsName = originalArrearTemplate.getCommodityName();
            String phoneNumber = originalArrearTemplate.getPhoneNumber();
            String deviceStatus = originalArrearTemplate.getDeviceStatus();
            String productInstance = originalArrearTemplate.getProductInstance();
            String commodityCase = originalArrearTemplate.getCommodityCase();
            String effective = "";
            String charge = "";
            String billingType = "";
            String billingLabel = "";
            int arrearAge = 0;
            float arrearAmount = 0;

            List<String> packageList = new ArrayList<>();
            Map<String, String> packageMap = new HashMap<>();

            if (!originalArrearTemplate.getArrearAge().equals("")) {
                arrearAge = Integer.parseInt(originalArrearTemplate.getArrearAge());
            }
            if (!originalArrearTemplate.getArrearAmount().equals("")) {
                arrearAmount = Float.parseFloat(originalArrearTemplate.getArrearAmount());
            }
        /*
         * 获取功能包，计费标签等信息
         */
            if (goodsName != null && !goodsName.equals("")) {
                packageMap.put(goodsName, "有");
            }
            packageMap.put("是否含附属产品23020027", originalArrearTemplate.getSetMea23020027());
            packageMap.put("是否含定价300110676", originalArrearTemplate.getSetMeal300110676());
            packageMap.put("是否含定价300115156", originalArrearTemplate.getSetMeal300115156());
            packageMap.put("是否含定价300121616", originalArrearTemplate.getSetMeal300121616());
            packageMap.put("是否含定价300123703", originalArrearTemplate.getSetMeal300123703());
            packageMap.put("是否含定价300133506", originalArrearTemplate.getSetMeal300133506());
            packageMap.put("是否含定价300140736", originalArrearTemplate.getSetMeal300140736());
            packageMap.put("是否含定价300141804", originalArrearTemplate.getSetMeal300141804());
            packageMap.put("是否含定价300142333", originalArrearTemplate.getSetMeal300142333());
            for (String key : packageMap.keySet()) {
                if ("有".equals(packageMap.get(key)) || "有\r".equals(packageMap.get(key))){
                    packageList.add(key);
                }
            }
            try {
                if (packageList.size() > 0) {
                    PackageInfo packageInfo = arrearAnalysisService.getPackageInfoByPackageName(packageList);
                    if (packageInfo != null && packageInfo.getChargeLabel() != null && ("正常".equals(deviceStatus) || "新装".equals(deviceStatus)) && arrearAmount == 0) {
                        effective = "有效";
                    }
                    if (packageInfo != null && packageInfo.getChargeLabel() != null) {
                        charge = "收费";
                    }
                    if (packageInfo != null) {
                        billingType = packageInfo.getChargeType();
                        billingLabel = packageInfo.getChargeLabel();
                    }
                } else if (originalArrearTemplate.getDeviceStatus().equals("")) {
                    deviceStatus = "CRM无信息";
                }
                List<StudentInfo> studentInfos = getStudentInfoByPhoneNumber(originalArrearTemplate.getPhoneNumber());
                if (studentInfos.size() > 0) {
                    for (StudentInfo studentInfo : studentInfos) {
                        ArrearInfo arrearInfo = new ArrearInfo();
                        if (studentInfo != null) {
                            arrearInfo.setPhoneNumber(phoneNumber);
                            arrearInfo.setDeviceStatus(deviceStatus);
                            arrearInfo.setCommodityInstance(commodityCase);
                            arrearInfo.setArrearAmount(arrearAmount);
                            arrearInfo.setArrearAge(arrearAge);
                            arrearInfo.setProductInstance(productInstance);
                            arrearInfo.setGoodsName(goodsName);
                            arrearInfo.setBillingLabel(billingLabel);
                            arrearInfo.setBillingType(billingType);
                            arrearInfo.setCharge(charge);
                            arrearInfo.setEffective(effective);
                            arrearInfo.setTemplateId(templateId);
                            arrearInfo.setUserId(studentInfo.getUserId());
                            arrearInfo.setUserName(studentInfo.getUserName());
                            arrearInfo.setAreaId(studentInfo.getAreaId());
                            arrearInfo.setAreaName(studentInfo.getAreaName());
                            arrearInfo.setSchoolId(studentInfo.getSchoolId());
                            arrearInfo.setShoolName(studentInfo.getSchoolName());
                            arrearInfo.setClassId(studentInfo.getClassId());
                            arrearInfo.setClassName(studentInfo.getClassName());
                            arrearInfo.setStudentId(studentInfo.getStudentId());
                            arrearInfo.setStudentName(studentInfo.getStudentName());
                            arrearInfo.setPayNumber(studentInfo.getPayNumber());
                            arrearInfo.setLoginNumber(studentInfo.getLoginNumber());
                        }
                        arrearInfoList.add(arrearInfo);
                    }
                } else {
                    ArrearInfo arrearInfo = new ArrearInfo();
                    arrearInfo.setPhoneNumber(phoneNumber);
                    arrearInfo.setDeviceStatus(deviceStatus);
                    arrearInfo.setProductInstance(productInstance);
                    arrearInfo.setCommodityInstance(commodityCase);
                    arrearInfo.setArrearAmount(arrearAmount);
                    arrearInfo.setArrearAge(arrearAge);
                    arrearInfo.setGoodsName(goodsName);
                    arrearInfo.setBillingLabel(billingLabel);
                    arrearInfo.setBillingType(billingType);
                    arrearInfo.setCharge(charge);
                    arrearInfo.setEffective(effective);
                    arrearInfo.setTemplateId(templateId);
                    arrearInfoList.add(arrearInfo);
                }

            } catch (Exception e) {

            }
        }
        arrearAnalysisService.saveArrearInfo(arrearInfoList);
    }

    /*
     *根据查询号码查询学生Id
     */
    List<StudentInfo> getStudentInfoByPhoneNumber(String phoneNumber) {
        List<StudentInfo> studentInfos = new ArrayList<StudentInfo>();
        if (!PhoneUtil.checkPhoneNumberFormatDX(phoneNumber)) {
            return null;
        }

        StudentInfo studentInfo = arrearAnalysisService.getStudentInfoByPayNumber("%" + phoneNumber);
        if (studentInfo != null) {
            studentInfos.add(studentInfo);
        }
        List<StudentInfo> tempStudentInfos = arrearAnalysisService.getStudentInfoByLoginNumber("%" + phoneNumber);
        if (tempStudentInfos != null ) {
            for (StudentInfo studentInfo1 : tempStudentInfos) {
                if (studentInfo1.getPayNumber() != null && !PhoneUtil.checkPhoneNumberFormatDX(String.valueOf(studentInfo1.getPayNumber()))) {
                    studentInfos.add(studentInfo1);
                }
            }
        }
        return studentInfos;
    }
}
