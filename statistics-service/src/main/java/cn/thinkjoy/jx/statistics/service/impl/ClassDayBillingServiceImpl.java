/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: statistics
 * $Id:  IBillingInfoService.java 2015-09-18 11:11:27 $
 */

package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IClassDayBillingDAO;
import cn.thinkjoy.jx.statistics.domain.ClassDayBilling;
import cn.thinkjoy.jx.statistics.service.IClassDayBillingService;
import cn.thinkjoy.jx.statistics.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("ClassDayBillingServiceImpl")
public class ClassDayBillingServiceImpl extends AbstractPageService<IBaseDAO<ClassDayBilling>,ClassDayBilling> implements IClassDayBillingService<IBaseDAO<ClassDayBilling>,ClassDayBilling> {
    @Autowired
    private IClassDayBillingDAO classDayBillingDAO;

    @Override
    public IBaseDAO<ClassDayBilling> getDao(){
        return classDayBillingDAO;
    };

    /**
     * 分页
     *
     * @param map
     * @param offset
     * @param rows
     * @param orderBy
     * @param sortBy
     * @return
     */
    public Page<ClassDayBilling> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy,int queryType) {
        Page<ClassDayBilling> page = new Page<>();
        if(queryType== Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM){
            Integer count = classDayBillingDAO.countByPageTime(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(classDayBillingDAO.queryByPageTime(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }else if(queryType==Constants.WHERR_AREAID_QUERY_SCHOOL_NUM){
            Integer count = classDayBillingDAO.countByPageTimeAndAreaId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(classDayBillingDAO.queryByPageTimeAndAreaId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }

        }else {
            Integer count = classDayBillingDAO.countByPageTimeAndSchoolId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(classDayBillingDAO.queryByPageTimeAndSchoolId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }

    }


    public List<ClassDayBilling> queryByTime(Map<String, Object> map) {
        List<ClassDayBilling> list=classDayBillingDAO.queryByTime(map);
        if(list!=null && list.size()>0){
            return  list;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }



}