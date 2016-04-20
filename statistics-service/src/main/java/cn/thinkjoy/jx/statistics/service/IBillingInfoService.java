/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: statistics
 * $Id:  IBillingInfoService.java 2015-09-18 11:11:27 $
 */

package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.jx.statistics.domain.BillingInfo;
import cn.thinkjoy.zgk.zgksystem.common.Page;

import java.util.Map;

public interface IBillingInfoService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{
    Page<BillingInfo> queryByPage(Map<String,Object> map, int offset, int rows, String orderBy, String sortBy);

    //导入欠费数据  苏州 20151217
    int importInsert(BillingInfo bi);

    //更新 userId 字段  苏州 20151218
    int updateUserId();

    //删除无效用户 （账户id不存在）  苏州 20151218
    int deleteInvalidUserId();

    //批量更新导入的欠费数据  苏州 20151218
    int updateImportBatch();

    //删除旧数据  苏州 20151218
    int deleteOldBillingInfo();

    int deleteDifferentNumber();
    //将新导入数据新旧标示修改为旧数据  苏州 20151218
    int updateNewBillingInfo();

    //清空统计数据  苏州 20151223
    int deleteBillingStatistics();

    //填入统计数据  苏州 20151223
    int updateBillingStatistics();

    //删除历史数据
    int deleteHistoryData();
}
