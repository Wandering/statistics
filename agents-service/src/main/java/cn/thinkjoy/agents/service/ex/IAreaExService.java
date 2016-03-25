package cn.thinkjoy.agents.service.ex;

import cn.thinkjoy.agents.dao.ex.IAreaExDAO;

import java.util.List;
import java.util.Map;

public interface IAreaExService{
    IAreaExDAO getDao();
    List<Map<String,Object>> getFlowNextArea();
    List<Map<String, Object>> getFlowNextArea(String nextArea);
}
