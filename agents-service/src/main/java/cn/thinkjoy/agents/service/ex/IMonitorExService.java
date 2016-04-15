package cn.thinkjoy.agents.service.ex;

import cn.thinkjoy.agents.dao.ex.IMonitorExDAO;
import cn.thinkjoy.agents.service.ex.common.IBaseExService;
import cn.thinkjoy.jx.statistics.domain.pojo.MonitorPojo;

import java.util.List;
import java.util.Map;

public interface IMonitorExService extends IBaseExService {
    public IMonitorExDAO getDao();
    public List<MonitorPojo> errorChart(Map<String,Object> map);
}
