package cn.thinkjoy.agents.service.ex.common;

import cn.thinkjoy.agents.dao.ex.ICardExDAO;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.utils.SqlOrderEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/3/16.
 */
public interface IBaseExService {
    public BizData4Page queryPageByDataPerm(Map<String, Object> condition, int page, int rows, String orderBy, SqlOrderEnum sqlOrderEnum, Map<String, Object> selector);
    public BizData4Page queryPageByDataPerm(BizData4Page bizData4Page, String orderBy, SqlOrderEnum sqlOrderEnum, Map<String, Object> selector);

    public ICardExDAO getDao();

    public  List<Map<String,Object>> queryPage(Map<String, Object> condition, int offset,
                                               int rows, String orderBy, String sortBy);


    public  List<Map<String,Object>>  queryPage(Map<String, Object> condition, int offset, int rows,
                                                String orderBy, String sortBy, Map<String, Object> selector);

    public  int count(Map<String, Object> condition);
}
