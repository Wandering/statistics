package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.jx.statistics.domain.UserDayLogin;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import cn.thinkjoy.zgk.zgksystem.common.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 *
 * 日班级用户登录次数汇总
 *
 */
public interface IUserDayLoginService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
    Page<UserDayLogin> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy);
    List<StatisticsPojo> queryByTime(Map<String,Object> map);
    Page<StatisticsPojo> queryByPageTime(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy, int queryType);



    //List<StatisticsPojo> queryLoginUserCountByTime(String range,String dateDay) throws ParseException;
    List<StatisticsPojo> queryLoginUserCountByTime(Map<String, Object> map,String range,String dateDay) throws ParseException;



    Page<StatisticsPojo> queryLoginUserCountByPageTime(Map<String, Object> map,String dateDay,String start,String end,int offset,int rows,int queryType) throws ParseException;
    Page<StatisticsPojo> queryLoginUserCountByPageTime(Map<String, Object> map,String dateDay,String start,String end,int offset,int rows,int queryType,int queryId) throws ParseException;
}
