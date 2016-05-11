package cn.thinkjoy.agents.service.ex;

import cn.thinkjoy.agents.dao.ex.IAreaExDAO;
import cn.thinkjoy.jx.statistics.pojo.AreaPojo;

import java.util.List;
import java.util.Map;

public interface IAreaExService{
    IAreaExDAO getDao();
    List<Map<String,Object>> getFlowNextArea();
    List<Map<String, Object>> getFlowNextArea(String nextArea);

    /**
     * 获取所有省份信息
     *
     * @return
     */
    List<AreaPojo> getAllProvince();

    /**
     * 获取所有省份信息
     *
     * @return
     */
    List<AreaPojo> getAllCity();

    /**
     * 获取所有省份信息
     *
     * @return
     */
    List<AreaPojo> getAllCounty();
}
