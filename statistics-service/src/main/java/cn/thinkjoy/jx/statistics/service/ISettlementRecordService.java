/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: system
 * $Id:  SettlementRecordService.java 2016-04-16 12:41:14 $
 */

package cn.thinkjoy.jx.statistics.service;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;

public interface ISettlementRecordService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{

}
