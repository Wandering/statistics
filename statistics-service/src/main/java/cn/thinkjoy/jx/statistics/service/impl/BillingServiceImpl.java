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
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IBillingDAO;
import cn.thinkjoy.jx.statistics.domain.Billing;
import cn.thinkjoy.jx.statistics.service.IBillingService;
import cn.thinkjoy.jx.statistics.util.Constants;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("BillingServiceImpl")
public class BillingServiceImpl extends AbstractPageService<IBaseDAO<Billing>, Billing> implements IBillingService<IBaseDAO<Billing>,Billing> {
    @Autowired
    private IBillingDAO billingDAO;

    @Override
    public IBaseDAO<Billing> getDao(){
        return billingDAO;
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
    public Page<Billing> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy, int queryType) {
        Page<Billing> page = new Page<>();
        if(queryType== Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM){//beteween_dateday_query_area_num
            Integer count = billingDAO.countByPage(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(billingDAO.queryByPage(map,offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }else if(queryType==Constants.WHERR_AREAID_QUERY_SCHOOL_NUM){
            Integer count = billingDAO.countByPageTimeAndAreaId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(billingDAO.queryByPageTimeAndAreaId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }

        }else {
            Integer count = billingDAO.countByPageTimeAndSchoolId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(billingDAO.queryByPageTimeAndSchoolId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }

    }

}