package cn.thinkjoy.jx.statistics.controller.agents.common;

import cn.thinkjoy.agents.service.ex.common.IBaseExService;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.utils.SqlOrderEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/3/16.
 */
public class BaseCommonController <T extends IBaseExService>{
    private static String DEFAULTORDERBY="id";
    public BizData4Page doPage(Integer page,Integer rows){

        return getService().queryPageByDataPerm(getCondition(),page,rows,getDefaultOrderBy(), getSqlOrder(),getSelector());
    }

    protected T getService(){
        return null;
    }

    protected Map<String,Object> getSelector(){
        return null;
    }

    protected Map<String,Object> getCondition(){
        return new HashMap<>();
    }

    protected SqlOrderEnum getSqlOrder(){
        return SqlOrderEnum.DESC;
    }

    protected String getDefaultOrderBy(){
        return DEFAULTORDERBY;
    }
}
