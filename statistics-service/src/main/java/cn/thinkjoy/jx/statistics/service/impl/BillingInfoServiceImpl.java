/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: statistics
 * $Id:  BillingInfoServiceImpl.java 2015-09-18 11:11:27 $
 */
package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IBillingInfoDAO;
import cn.thinkjoy.jx.statistics.domain.BillingInfo;
import cn.thinkjoy.jx.statistics.service.IBillingInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("BillingInfoServiceImpl")
public class BillingInfoServiceImpl extends AbstractPageService<IBaseDAO<BillingInfo>, BillingInfo> implements IBillingInfoService<IBaseDAO<BillingInfo>,BillingInfo> {
    @Autowired
    private IBillingInfoDAO billingInfoDAO;

    @Override
    public IBaseDAO<BillingInfo> getDao() {
        return billingInfoDAO;
    }
    /**
     * 分页
     * @param map
     * @param offset
     * @param rows
     * @param orderBy
     * @param sortBy
     * @return
     */
    public Page<BillingInfo> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy){
        Page<BillingInfo> page=new Page<>();
        Integer count=billingInfoDAO.totalCount(map);
        if(count>0){
            page.setCount(count);
            page.setList(billingInfoDAO.queryByPageAndCondition(map, offset, rows, orderBy, sortBy));
            return page;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    //导入欠费数据  苏州 20151217
    public int importInsert(BillingInfo bi) { return billingInfoDAO.importInsert(bi); }


    //更新 userId 字段  苏州 20151218
    public int updateUserId(){
        return billingInfoDAO.updateUserId();
    }

    //删除无效用户 苏州 20151218
    public int deleteInvalidUserId(){
        return billingInfoDAO.deleteInvalidUserId();
    }

    //批量更新导入的欠费数据  苏州 20151218
    public int updateImportBatch(){
        return billingInfoDAO.updateImportBatch();
    }
    //删除旧数据  苏州 20151218
    public int deleteOldBillingInfo(){
        return billingInfoDAO.deleteOldBillingInfo();
    }

    @Override
    public int deleteDifferentNumber() {
        return billingInfoDAO.deleteDifferentNumber();
    }

    //将新导入数据新旧标示修改为旧数据  苏州 20151218
    public int updateNewBillingInfo(){
        return billingInfoDAO.updateNewBillingInfo();
    }

    //清空统计数据  苏州 20151223
    public int deleteBillingStatistics(){
        return billingInfoDAO.deleteBillingStatistics();
    }

    //填入统计数据  苏州 20151223
    public int updateBillingStatistics(){
        return billingInfoDAO.updateBillingStatistics();
    }

    @Override
    public int deleteHistoryData() {
        return billingInfoDAO.deleteHistoryData();
    }


//    @Override
//    public void insert(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void update(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void updateNull(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void deleteById(Long id) {
//
//    }
//
//    @Override
//    public void deleteByProperty(String property, Object value) {
//
//    }
//
//    @Override
//    public BaseDomain fetch(Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain findOne(@Param("property") String property, @Param("value") Object value) {
//        return null;
//    }
//
//    @Override
//    public List findList(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public void deleteByCondition(Map condition) {
//
//    }
//
//    @Override
//    public void updateMap(@Param("map") Map entityMap) {
//
//    }
//
//    @Override
//    public List<Company> findAll() {
//        return companyDAO.findAll();
//    }
//
//    @Override
//    public List like(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public Long selectMaxId() {
//        return null;
//    }
//
//    @Override
//    public BaseDomain updateOrSave(BaseDomain baseDomain, Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain selectOne(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public List selectList(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public Class getEntityClass() {
//        return null;
//    }
//
//    @Override
//    public int count(Map condition) {
//        return 0;
//    }
//
//    @Override
//    public BaseDomain queryOne(Map condition) {
//        return null;
//    }
//
//    @Override
//    public List queryList(@Param("condition") Map condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy) {
//        return null;
//    }
//
//    @Override
//    public List queryPage(@Param("condition") Map condition, @Param("offset") int offset, @Param("rows") int rows) {
//        return null;
//    }
//
//    @Override
//    protected CompanyDAO getDao() {
//        return companyDAO;
//    }
//
//    @Override
//    public BizData4Page queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows) {
//        return super.doQueryPageByDataPerm(resUri, conditions, curPage, offset, rows);
//    }


}
