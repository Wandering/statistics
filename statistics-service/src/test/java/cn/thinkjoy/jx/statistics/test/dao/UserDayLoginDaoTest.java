package cn.thinkjoy.jx.statistics.test.dao;

import cn.thinkjoy.jx.statistics.dao.IUserDayLoginDAO;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jzli on 15/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:statistics-spring.xml")
public class UserDayLoginDaoTest {

    @Autowired
    private IUserDayLoginDAO userDayLoginDAO;

    @Test
    public void queryTest() {
        List<StatisticsPojo> list = new ArrayList<>();
        Map<String, Object> conditions = Maps.newHashMap();

        conditions.put("dateStart", "2015-09-01");
        conditions.put("dateEnd", "2015-09-30");
        conditions.put("minRange", "1");
        conditions.put("maxRange", "5");
        StatisticsPojo obj = userDayLoginDAO.queryLoginUserCountByTime(conditions);
        System.out.println(obj);
    }

    @Test
    public void queryPageByAreaTest() {
        Map<String, Object> conditions = Maps.newHashMap();
        conditions.put("minRange", 1);
        conditions.put("maxRange", 5);
        conditions.put("dateStart", "2015-09-01");
        conditions.put("dateEnd", "2015-09-30");
        conditions.put("offset", 0);
        conditions.put("rows", 10);
        List<StatisticsPojo> list = userDayLoginDAO.queryLoginUserCountByPageTimeAndAreaId(conditions, 0, 10);
        int count = userDayLoginDAO.countLoginUserCountByPageTimeAndAreaId(conditions);
        System.out.println(list);
        System.out.println(count);
    }

    @Test
    public void queryPageBySchoolTest() {
        Map<String, Object> conditions = Maps.newHashMap();
        conditions.put("minRange", 1);
        conditions.put("maxRange", 5);
        conditions.put("dateStart", "2015-09-01");
        conditions.put("dateEnd", "2015-09-30");
        conditions.put("areaId", 5);
        conditions.put("offset", 0);
        conditions.put("rows", 10);
        List<StatisticsPojo> list = userDayLoginDAO.queryLoginUserCountByPageTimeAndSchoolId(conditions, 0, 10);
        int count = userDayLoginDAO.countLoginUserCountByPageTimeAndSchoolId(conditions);
        System.out.println(list);
        System.out.println(count);
    }

    @Test
    public void queryPageByClassTest() {
        Map<String, Object> conditions = Maps.newHashMap();
        conditions.put("minRange", 1);
        conditions.put("maxRange", 5);
        conditions.put("dateStart", "2015-09-01");
        conditions.put("dateEnd", "2015-09-30");
        conditions.put("schoolId", 1);
        conditions.put("offset", 0);
        conditions.put("rows", 10);
        List<StatisticsPojo> list = userDayLoginDAO.queryLoginUserCountByPageTimeAndClassId(conditions, 0, 10);
        int count = userDayLoginDAO.countLoginUserCountByPageTimeAndClassId(conditions);
        System.out.println(list);
        System.out.println(count);
    }
}
