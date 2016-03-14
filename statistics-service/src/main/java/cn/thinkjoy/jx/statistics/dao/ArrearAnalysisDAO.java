package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 2015/12/18.
 */
public interface ArrearAnalysisDAO extends IBaseDAO<ArrearInfo> {

    public PackageInfo getPackageInfoByPackageName(List nameList);
    public StudentInfo getStudentInfoByPayNumber(String payNumber);
    public List<StudentInfo> getStudentInfoByLoginNumber(String loginNumber);
    public ArrearInfo getArrearInfoByPhoneNumberTemplateId(Map queryParam);
    public StudentInfo getStudentInfoBystudentId(long studentId);
    public int saveTemplateInfo(TemplateInfo templateInfo);
    public void saveArrearInfo(List<ArrearInfo> arrearInfo);
    public void updateArearInfo(ArrearInfo arrearInfo);
    public List<TemplateInfo> getArrearImportHistory();
    public void updateTemplateInfo(@Param("condition")Map paramMap);
    public List<TemplateInfo> getTemplateInfoHistory(@Param("condition")Map<String, Object> map, @Param("offset")int offset, @Param("rows")int rows, @Param("orderBy")String orderBy, @Param("sortBy")String sortBy);
    public long countRowNum();
    public List<OriginalArrearTemplate> getOriginalArrearTemplate(@Param("offset")int offset);
    public int saveAllData(String fileName);
    //保存处理过的数据
    public int saveDealData(String fileName);
    //更新保存数据templateId
    public int updateTemplateId(long templateId);
    public List<ArrearInfo> getArrearInfoByPage(@Param("condition")Map<String, Object> map, @Param("offset")int offset, @Param("rows")int rows, @Param("orderBy")String orderBy,@Param("sortBy") String sortBy);
    void savePageUserInfo(int var1);
    void saveCityUserInfo(int var1);
    void saveSuburbanite(int var1);
    void saveTotalUserInfo(int var1);
    void saveOutUserInfo(int var1);
    public List<PageUserInfo> queryPageUser(Long var1);
    List<String> queryNumberByTemplateId(Long var1);
    //根据时间查询是否有记录  suzhou 20151223
    public Long checkDate(String date);

    //计算欠费数据导入次数
    public int countImportNum();
    public int countArrearNum(@Param("condition")Map<String, Object> map);
    public int countErrorData(int templateId);
    //删除临时表数据
    public void deleteTempData();
}