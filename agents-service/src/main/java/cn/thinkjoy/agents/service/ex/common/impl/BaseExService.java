package cn.thinkjoy.agents.service.ex.common.impl;

import cn.thinkjoy.agents.service.ex.common.IBaseExService;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.utils.SqlOrderEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/3/16.
 */
public abstract class BaseExService implements IBaseExService {
    /***
     * 条件查询包含各种查询
     * baseDAO 主要为扩展exdao
     * @param bizData4Page
     */
    public BizData4Page queryPageByDataPerm(BizData4Page bizData4Page, String orderBy, SqlOrderEnum sqlOrderEnum,Map<String,Object> selector)
    {
        int offset = (bizData4Page.getPage()-1)*bizData4Page.getPagesize();
        int rows = bizData4Page.getPagesize();
        List<Map<String,Object>> mainData = queryPage(bizData4Page.getConditions(), offset, rows, orderBy, sqlOrderEnum.getAction(), selector);
        int records =  count(bizData4Page.getConditions());
        //对list进行额外处理
        mainDataHandler(mainData);
        bizData4Page.setRows(mainData);
        bizData4Page.setPage(bizData4Page.getPage());
        bizData4Page.setRecords(records);

        int total = records / rows;
        int mod = records % rows;
        if(mod > 0){
            total = total + 1;
        }
        bizData4Page.setTotal(total);
        return bizData4Page;
    }

    /***
     * 条件查询包含各种查询
     * baseDAO 主要为扩展exdao
     */
    public BizData4Page queryPageByDataPerm(Map<String,Object> condition,int page,int rows, String orderBy, SqlOrderEnum sqlOrderEnum,Map<String,Object> selector)
    {
        BizData4Page bizData4Page=new BizData4Page();
        int offset = (page - 1) * rows;
        conditionHandler(condition);
        List<Map<String,Object>> mainData = queryPage(condition, offset, rows, orderBy, sqlOrderEnum.getAction(), selector);
        int records =  count(condition);
        mainDataHandler(mainData);
        bizData4Page.setRows(mainData);
        bizData4Page.setPage(bizData4Page.getPage());
        bizData4Page.setRecords(records);

        int total = records / rows;
        int mod = records % rows;
        if(mod > 0){
            total = total + 1;
        }
        bizData4Page.setTotal(total);
        return bizData4Page;
    }

    public abstract List<Map<String,Object>>  queryPage(Map<String, Object> condition,int offset,
                                                 int rows,String orderBy,String sortBy);


    public abstract List<Map<String,Object>>  queryPage(Map<String, Object> condition ,int offset, int rows,
                                        String orderBy,String sortBy, Map<String, Object> selector);

    public abstract int count(Map<String, Object> condition);

    /**
     * 对List进行额外处理
     * @param list
     */
    protected void mainDataHandler(List list){}
    protected void conditionHandler(Map<String,Object> condition){
        condition.put("groupOp", "and");
    }



}
