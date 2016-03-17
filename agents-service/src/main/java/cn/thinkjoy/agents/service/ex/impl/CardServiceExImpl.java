/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  AreabatchLineServiceImpl.java 2015-12-16 09:46:54 $
 */
package cn.thinkjoy.agents.service.ex.impl;

import cn.thinkjoy.agents.dao.ex.IAreaExDAO;
import cn.thinkjoy.agents.dao.ex.ICardExDAO;
import cn.thinkjoy.agents.service.ex.ICardExService;
import cn.thinkjoy.agents.service.ex.common.AgentsInfoUtils;
import cn.thinkjoy.agents.service.ex.common.impl.BaseExService;
import cn.thinkjoy.common.domain.SearchField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("CardExServiceImpl")
@Scope("prototype")
public class CardServiceExImpl extends BaseExService implements ICardExService {
    @Autowired
    private ICardExDAO cardExDAO;
    @Autowired
    private IAreaExDAO areaExDAO;
    @Override
    public ICardExDAO getDao() {
        return cardExDAO;
    }

    @Override
    public List<Map<String, Object>> queryPage(Map<String, Object> condition, int offset, int rows, String orderBy, String sortBy) {
        return getDao().queryPage(condition,offset,rows,orderBy,sortBy);
    }

    @Override
    public List<Map<String, Object>> queryPage(Map<String, Object> condition, int offset, int rows, String orderBy, String sortBy, Map<String, Object> selector) {
        return getDao().queryPage(condition,offset,rows,orderBy,sortBy,selector);
    }

    @Override
    public int count(Map<String, Object> condition) {
        return getDao().count(condition);
    }


    /**
     * 出库操作
     * @return
     */
    @Override
    public boolean goodsOutput(Map<String, Object> condition) {
        if(condition.containsKey("userArea")){
            return getDao().output(condition)>0;
        }
        return false;
    }





    @Override
    protected void mainDataHandler(List list) {
        List<Map<String,Object>> listMap=(List<Map<String,Object>>)list;
        AgentsInfoUtils.setGoodsStatusAndStorageDate(listMap);
        AgentsInfoUtils.setAreaExDAO(areaExDAO);
        AgentsInfoUtils.setFlow(listMap);
    }

    @Override
    protected void conditionHandler(Map<String, Object> condition) {
        super.conditionHandler(condition);
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
//    public List<AreabatchLine> findAll() {
//        return areabatchLineDAO.findAll();
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
//    protected AreabatchLineDAO getDao() {
//        return areabatchLineDAO;
//    }
//
//    @Override
//    public BizData4Page queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows) {
//        return super.doQueryPageByDataPerm(resUri, conditions, curPage, offset, rows);
//    }


}
