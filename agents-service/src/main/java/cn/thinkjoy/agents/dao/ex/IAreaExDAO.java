package cn.thinkjoy.agents.dao.ex;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/3/16.
 */
public interface IAreaExDAO {
    public List<Map<String,Object>> queryCity();
    public String queryCityById(String id);
    public List<Map<String,Object>> queryCounty();
    public String queryCountyById(String id);
    public List<Map<String,Object>> queryProvince();
    public String queryProvinceById(String id);
    public List<Map<String,Object>> likeProvince();
    public List<Map<String,Object>> likeCountyById(String id);
    public List<Map<String,Object>> likeCityById(String id);
    public List<String> getAgentsAreas(String areaCode);
}
