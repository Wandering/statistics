package cn.thinkjoy.agents.dao.ex;

import cn.thinkjoy.agents.dao.ex.common.BaseCommonExDAO;
import cn.thinkjoy.jx.statistics.domain.pojo.MonitorPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/3/17.
 */
public interface IMonitorExDAO extends BaseCommonExDAO{
    public List<MonitorPojo> errorChart(Map<String,Object> map);
}
