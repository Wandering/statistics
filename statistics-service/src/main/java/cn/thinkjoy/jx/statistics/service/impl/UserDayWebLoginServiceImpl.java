package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IUserDayWebLoginDAO;
import cn.thinkjoy.jx.statistics.domain.UserDayLogin;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IUserDayWebLoginService;
import cn.thinkjoy.jx.statistics.util.Constants;
import cn.thinkjoy.jx.statistics.util.DateUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenmeng on 16/2/19.
 *
 * 日班级用户登录次数汇总
 *
 */
@Service("UserDayWebLoginServiceImpl")
public class UserDayWebLoginServiceImpl extends AbstractPageService<IBaseDAO<UserDayLogin>,UserDayLogin> implements IUserDayWebLoginService<IBaseDAO<UserDayLogin>,UserDayLogin> {
    @Autowired
    private IUserDayWebLoginDAO userDayLoginDAO;

    @Override
    public IBaseDAO<UserDayLogin> getDao() {
        return userDayLoginDAO;
    }

    ;

    /**
     * 分页
     *
     * @param map
     * @param offset
     * @param rows
     * @param orderBy
     * @param sortBy
     * @return
     */
    public Page<UserDayLogin> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy) {
        Page<UserDayLogin> page = new Page<>();
        Integer count = userDayLoginDAO.totalCount(map);
        if (count > 0) {
            page.setCount(count);
            page.setList(userDayLoginDAO.queryByPageAndCondition(map, offset, rows, orderBy, sortBy));
            return page;
        } else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * @param map
     * @return
     */
    public List<StatisticsPojo> queryByTime(Map<String, Object> map) {
        List<StatisticsPojo> list = userDayLoginDAO.queryByTime(map);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 分页
     *
     * @param map
     * @param offset
     * @param rows
     * @param orderBy
     * @param sortBy
     * @return
     */
    public Page<StatisticsPojo> queryByPageTime(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy, int queryType) {
        Page<StatisticsPojo> page = new Page<>();
        if (queryType == Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM) {
            Integer count = userDayLoginDAO.countByPageTime(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(userDayLoginDAO.queryByPageTime(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        } else if (queryType == Constants.WHERR_AREAID_QUERY_SCHOOL_NUM) {
            Integer count = userDayLoginDAO.countByPageTimeAndAreaId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(userDayLoginDAO.queryByPageTimeAndAreaId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }

        } else {
            Integer count = userDayLoginDAO.countByPageTimeAndSchoolId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(userDayLoginDAO.queryByPageTimeAndSchoolId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }

    }

    @Override
    public List<StatisticsPojo> queryLoginUserCountByTime(Map<String, Object> map, String range, String dateDay) throws ParseException {
        List<StatisticsPojo> list = new ArrayList<>();
        Map<String, Object> condition = new HashMap<>();
        String firstDay = DateUtil.dateFormateChange(dateDay, "yyyy-MM", "yyyy-MM-01");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(firstDay);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE,-1);
        Date lastDate = cal.getTime();
        String lastDay = format.format(lastDate);
        condition.put("dateStart", firstDay);
        condition.put("dateEnd", lastDay);




        condition.put("schoolIds", map.get("schoolIds"));

        condition.put("areaIds", map.get("areaIds"));




        String[] st = lastDay.split("-");
        String sl = st[2];
        if (StringUtils.isBlank(range)) {
            range = "1-5,6-10,11-15,16-20,21-25,26-"+sl;
        }
        String[] str = range.split(",");
        for (String r : str) {
            String[] s = r.split("-");
            condition.put("minRange", s[0]);
            condition.put("maxRange", s[1]);
            StatisticsPojo pojo = userDayLoginDAO.queryLoginUserCountByTime(condition);
            if (pojo != null) {
                list.add(pojo);
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }
        return list;
    }

    @Override
    public Page<StatisticsPojo> queryLoginUserCountByPageTime(Map<String, Object> map,String dateDay, String start, String end, int offset, int rows, int queryType) throws ParseException {
        Page<StatisticsPojo> page = new Page<>();
        List<StatisticsPojo> list = new ArrayList<>();
        Map<String, Object> condition = new HashMap<>();
        String firstDay = DateUtil.dateFormateChange(dateDay, "yyyy-MM", "yyyy-MM-01");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(firstDay);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        Date lastDate = cal.getTime();
        String lastDay = format.format(lastDate);
        condition.put("dateStart", firstDay);
        condition.put("dateEnd", lastDay);
        condition.put("minRange", start);
        condition.put("maxRange", end);


        condition.put("areaIds", map.get("areaIds"));
        condition.put("schoolIds", map.get("schoolIds"));


        Integer count = userDayLoginDAO.countLoginUserCountByPageTimeAndAreaId(condition);
        if (count > 0) {
            page.setCount(count);
            page.setList(userDayLoginDAO.queryLoginUserCountByPageTimeAndAreaId(condition, offset, rows));
        } else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return page;
    }

    @Override
    public Page<StatisticsPojo> queryLoginUserCountByPageTime(Map<String, Object> map,String dateDay,String start, String end, int offset, int rows, int queryType,int queryId) throws ParseException {
        Page<StatisticsPojo> page = new Page<>();
        List<StatisticsPojo> list = new ArrayList<>();
        Map<String, Object> condition = new HashMap<>();
        String firstDay = DateUtil.dateFormateChange(dateDay, "yyyy-MM", "yyyy-MM-01");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(firstDay);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        Date lastDate = cal.getTime();
        String lastDay = format.format(lastDate);
        condition.put("dateStart", firstDay);
        condition.put("dateEnd", lastDay);
        condition.put("minRange", start);
        condition.put("maxRange", end);
        if (queryType == Constants.WHERR_AREAID_QUERY_SCHOOL_NUM) {
            condition.put("areaId", queryId);

            condition.put("schoolIds", map.get("schoolIds"));


            Integer count = userDayLoginDAO.countLoginUserCountByPageTimeAndSchoolId(condition);
            if (count > 0) {
                page.setCount(count);
                page.setList(userDayLoginDAO.queryLoginUserCountByPageTimeAndSchoolId(condition, offset, rows));
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        } else {
            condition.put("schoolId",queryId);
            Integer count = userDayLoginDAO.countLoginUserCountByPageTimeAndClassId(condition);
            if (count > 0) {
                page.setCount(count);
                page.setList(userDayLoginDAO.queryLoginUserCountByPageTimeAndClassId(condition, offset, rows));
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }
        return page;
    }

}