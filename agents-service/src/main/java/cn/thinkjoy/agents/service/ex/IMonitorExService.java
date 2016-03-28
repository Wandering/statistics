package cn.thinkjoy.agents.service.ex;

import cn.thinkjoy.agents.dao.ex.IMonitorExDAO;
import cn.thinkjoy.agents.service.ex.common.IBaseExService;

import java.util.List;
import java.util.Map;

public interface IMonitorExService extends IBaseExService {
    public IMonitorExDAO getDao();
    public List<Map<String,Object>> errorChart(Map<String,Object> map);
}
