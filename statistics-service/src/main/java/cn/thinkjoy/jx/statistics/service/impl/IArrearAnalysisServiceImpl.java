package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.dao.ArrearAnalysisDAO;
import cn.thinkjoy.jx.statistics.domain.*;
import cn.thinkjoy.jx.statistics.service.IArrearAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 2015/12/18.
 */
@Service("IArrearAnalysisServiceImpl")
public class IArrearAnalysisServiceImpl extends AbstractPageService<IBaseDAO<ArrearInfo>,ArrearInfo> implements IArrearAnalysisService<IBaseDAO<ArrearInfo>,ArrearInfo> {
    @Autowired
    private ArrearAnalysisDAO arrearAnalysisDAO;


    @Override
    public PackageInfo getPackageInfoByPackageName(List nameList) {
        return arrearAnalysisDAO.getPackageInfoByPackageName(nameList);
    }

    @Override
    public IBaseDAO<ArrearInfo> getDao() {
        return arrearAnalysisDAO;
    }

    @Override
    public StudentInfo getStudentInfoByPayNumber(String payNumber) {
        return arrearAnalysisDAO.getStudentInfoByPayNumber(payNumber);
    }

    @Override
    public List<StudentInfo> getStudentInfoByLoginNumber(String loginNumber) {
        return arrearAnalysisDAO.getStudentInfoByLoginNumber(loginNumber);
    }

    @Override
    public ArrearInfo getArrearInfoByPhoneNumberTemplateId(Map queryParam) {
        return arrearAnalysisDAO.getArrearInfoByPhoneNumberTemplateId(queryParam);
    }

    @Override
    public StudentInfo getStudentInfoBystudentId(long studentId) {
        return arrearAnalysisDAO.getStudentInfoBystudentId(studentId);
    }

    @Override
    public int saveTemplateInfo(TemplateInfo templateInfo) {
        return arrearAnalysisDAO.saveTemplateInfo(templateInfo);
    }

    @Override
    public void saveArrearInfo(List<ArrearInfo> arrearInfo) {
        arrearAnalysisDAO.saveArrearInfo(arrearInfo);
    }

    @Override
    public void updateArearInfo(ArrearInfo arrearInfo) {
        arrearAnalysisDAO.updateArearInfo(arrearInfo);
    }

    @Override
    public List<TemplateInfo> getArrearImportHistory() {
        return arrearAnalysisDAO.getArrearImportHistory();
    }

    @Override
    public void updateTemplateInfo(Map paramMap) {
        arrearAnalysisDAO.updateTemplateInfo(paramMap);
    }

    @Override
    public Page<TemplateInfo> getTemplateInfoHistory(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy) {
        Page<TemplateInfo> page = new Page<>();
        page.setList(arrearAnalysisDAO.getTemplateInfoHistory(map, offset, rows, orderBy, sortBy));
        page.setCount(arrearAnalysisDAO.countImportNum());
        return page;
    }

    @Override
    public long countRowNum() {
        return arrearAnalysisDAO.countRowNum();
    }

    @Override
    public List<OriginalArrearTemplate> getOriginalArrearTemplate(int offset) {
        return arrearAnalysisDAO.getOriginalArrearTemplate(offset);
    }

    @Override
    public int saveAllData(String fileName) {
        return arrearAnalysisDAO.saveAllData(fileName);
    }

    @Override
    public int saveDealData(String fileName) {
        return arrearAnalysisDAO.saveDealData(fileName);
    }

    @Override
    public int updateTemplateId(long templateId) {
        return arrearAnalysisDAO.updateTemplateId(templateId);
    }

    @Override
    public Page<ArrearInfo> getArrearInfoByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy) {
        Page<ArrearInfo> page = new Page<ArrearInfo>();
        List<ArrearInfo> arrearInfoList = arrearAnalysisDAO.getArrearInfoByPage(map, offset, rows, orderBy, sortBy);

        page.setCount(arrearAnalysisDAO.countArrearNum(map));
        page.setList(arrearInfoList);
        return page;
    }

    @Override
    public void savePageUserInfo(int templateId) {
        arrearAnalysisDAO.savePageUserInfo(templateId);
        arrearAnalysisDAO.saveCityUserInfo(templateId);
        arrearAnalysisDAO.saveSuburbanite(templateId);
        arrearAnalysisDAO.saveTotalUserInfo(templateId);
    }

    @Override
    public void saveOutUserInfo(int templateId) {
        arrearAnalysisDAO.saveOutUserInfo(templateId);
    }

    @Override
    public List<PageUserInfo> queryPageUser(Long templateId) {
        return arrearAnalysisDAO.queryPageUser(templateId);
    }

    @Override
    public List<String> queryNumberByTemplateId(Long templateId) {
        return arrearAnalysisDAO.queryNumberByTemplateId(templateId);
    }

    //根据时间查询是否有记录  suzhou 20151223
    public Long checkDate(String date){
        return arrearAnalysisDAO.checkDate(date + "%");
    }

    @Override
    public int countImportNum() {
        return arrearAnalysisDAO.countImportNum();
    }

    @Override
    public int countUserNum(Map<String, Object> map) {
        return arrearAnalysisDAO.countArrearNum(map);
    }

    @Override
    public int countErrorData(int templateId) {
        return arrearAnalysisDAO.countErrorData(templateId);
    }

    @Override
    public void deleteTempTableData() {
        arrearAnalysisDAO.deleteTempData();
    }

}
