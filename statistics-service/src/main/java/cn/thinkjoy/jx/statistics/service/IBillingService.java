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
import cn.thinkjoy.jx.statistics.domain.Billing;
import cn.thinkjoy.zgk.zgksystem.common.Page;

import java.util.Map;

public interface IBillingService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{
    Page<Billing> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy, int queryType);
}
