/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  AreabatchLineServiceImpl.java 2015-12-16 09:46:54 $
 */
package cn.thinkjoy.agents.service.ex.impl;

import cn.thinkjoy.agents.dao.ex.IAreaExDAO;
import cn.thinkjoy.agents.service.ex.IAreaExService;
import cn.thinkjoy.agents.service.ex.common.AgentsInfoUtils;
import cn.thinkjoy.jx.statistics.pojo.AreaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("AreaExServiceImpl")
@Scope("prototype")
public class AreaExServiceImpl implements IAreaExService {

    @Autowired
    private IAreaExDAO areaExDAO;

    @Override
    public IAreaExDAO getDao() {
        return areaExDAO;
    }

    @Override
    public List<Map<String, Object>> getFlowNextArea() {
        return AgentsInfoUtils.getFlowNextArea();
    }

    @Override
    public List<Map<String, Object>> getFlowNextArea(String nextArea) {
        return AgentsInfoUtils.getFlowNextArea(nextArea);
    }

    @Override
    public List<AreaPojo> getAllProvince() {
        return areaExDAO.getAllProvince();
    }

    @Override
    public List<AreaPojo> getAllCity() {
        return areaExDAO.getAllCity();
    }

    @Override
    public List<AreaPojo> getAllCounty() {
        return areaExDAO.getAllCounty();
    }
}
