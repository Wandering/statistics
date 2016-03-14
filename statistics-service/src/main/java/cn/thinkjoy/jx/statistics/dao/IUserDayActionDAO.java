package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.jx.statistics.domain.UserDayLogin;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chenmeng on 16/03/10.
 *
 * 日班级用户登录次数汇总
 *
 */
public interface IUserDayActionDAO extends IBaseDAO<UserDayLogin> {

    List<StatisticsPojo> queryByTime(@Param("condition") Map<String, Object> var1);

}
