/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: statistics
 * $Id:  IBillingInfoDAO.java 2015-08-31 11:11:27 $
 */
package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.domain.BillingInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IBillingInfoDAO extends IBaseDAO<BillingInfo>{

    Integer totalCount(@Param("condition") Map<String,Object> var1 );

    List<BillingInfo> queryByPageAndCondition(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);

    //导入欠费数据  苏州 20151217
    int importInsert(@Param("condition") BillingInfo var1);

    //更新 userId 字段  苏州 20151218
    int updateUserId();

    //删除无效用户 （账户id不存在）苏州 20151218
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
    public int deleteHistoryData();
}
