package cn.thinkjoy.agents.service.ex;

import cn.thinkjoy.agents.dao.ex.IMonitorExDAO;
import cn.thinkjoy.agents.service.ex.common.IBaseExService;

public interface IMonitorExService extends IBaseExService {
    public IMonitorExDAO getDao();
}
