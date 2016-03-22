package cn.thinkjoy.agents.service.ex.common;

import cn.thinkjoy.agents.dao.ex.IAreaExDAO;
import cn.thinkjoy.common.exception.BizException;
import com.google.common.cache.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2016/3/18.
 */
@Component
public class AreaCacheUtils {

    @Autowired
    private IAreaExDAO autoAreaExDAO;

    private static IAreaExDAO areaExDAO;

    private static LoadingCache<String,Map<String,Object>> areaCache
            //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
            = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
                    //设置写缓存后一周过期
            .expireAfterWrite(7, TimeUnit.DAYS)
                    //设置缓存容器的初始容量为10
            .initialCapacity(10)
                    //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(100)
                    //设置要统计缓存的命中率
            .recordStats()
                    //设置缓存的移除通知
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    System.out.println("缓存被删除！");
                }
            })
                    //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
            .build(
                    new CacheLoader<String,Map<String,Object>>() {
                        @Override
                        public Map<String,Object> load(String key) throws Exception {
                            if(PROVINCE.equals(key)) {
                                List<Map<String, Object>> provinceList = areaExDAO.queryProvince();
                                Map<String, Object> map = new HashMap<>();
                                for (Map<String, Object> province : provinceList) {
                                    String provinceKey = province.get("id").toString();
                                    map.put(provinceKey, province.get("name"));
                                }
                                return map;
                            }else if(CITY.equals(key)){
                                List<Map<String, Object>> cityList = areaExDAO.queryCity();
                                Map<String, Object> map = new HashMap<>();
                                for (Map<String, Object> city : cityList) {
                                    String provinceKey = city.get("id").toString();
                                    map.put(provinceKey, city.get("name"));
                                }
                                return map;
                            }else if(COUNTY.equals(key)){
                                List<Map<String, Object>> countyList = areaExDAO.queryCounty();
                                Map<String, Object> map = new HashMap<>();
                                for (Map<String, Object> county : countyList) {
                                    String provinceKey = county.get("id").toString();
                                    map.put(provinceKey, county.get("name"));
                                }
                                return map;
                            }
                            return null;
                        }
                        private String PROVINCE="province";
                        private String CITY="city";
                        private String COUNTY="county";
                    }
            );

    @PostConstruct
    public void initAreaExDAO() {
        AreaCacheUtils.areaExDAO = autoAreaExDAO;
    }

    public static String getAreaCache(String type,String key) {
        try {
            return areaCache.get(type).get(key).toString();
        } catch (ExecutionException e) {
            throw new BizException("error","获取缓存失败");
        }
    }
    private static String PROVINCE="province";
    private static String CITY="city";
    private static String COUNTY="county";
}
