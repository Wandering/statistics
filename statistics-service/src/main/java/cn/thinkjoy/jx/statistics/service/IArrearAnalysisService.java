package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.jx.statistics.domain.*;
import cn.thinkjoy.zgk.zgksystem.common.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by wenqiangwu on 15/9/18.
 *
 * 欠费数据处理分析
 *
 */
public interface IArrearAnalysisService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {

    PackageInfo getPackageInfoByPackageName(List<String> nameList);
    StudentInfo getStudentInfoByPayNumber(String payNumber);
    List<StudentInfo> getStudentInfoByLoginNumber(String loginNumber);
    ArrearInfo getArrearInfoByPhoneNumberTemplateId(Map queryParam);
    StudentInfo getStudentInfoBystudentId(long studentId);
    int saveTemplateInfo(TemplateInfo templateInfo);
    void saveArrearInfo(List<ArrearInfo> arrearInfo);
    void updateArearInfo(ArrearInfo arrearInfo);
    List<TemplateInfo> getArrearImportHistory();
    void updateTemplateInfo(Map paramMap);
    Page<TemplateInfo> getTemplateInfoHistory(Map<String, Object> map , int offset, int rows, String orderBy, String sortBy);
    public long countRowNum();
    List<OriginalArrearTemplate> getOriginalArrearTemplate(int offset);
    int saveAllData(String  fileName);
    //导入处理过的数据
    int saveDealData(String fileName);
    //更新导入数据templateId字段
    int updateTemplateId(long templateId);
    Page<ArrearInfo> getArrearInfoByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy);
    void savePageUserInfo(int templateId);
    void saveOutUserInfo(int templateId);
    List<PageUserInfo> queryPageUser(Long templateId);
    List<String> queryNumberByTemplateId(Long templateId);
    //根据时间查询是否有记录  suzhou 20151223
    Long checkDate(String date);
    //计算导入欠费数据次数
    int countImportNum();
    //根据templateId,areaId,dataType计算欠费用户数量
    int countUserNum(Map<String, Object> map);
    // 计算错误数据
    int countErrorData(int templateId);

    //删除临时表中的数据
    void deleteTempTableData();
}