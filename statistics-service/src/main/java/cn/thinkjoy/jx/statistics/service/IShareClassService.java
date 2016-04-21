package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.jx.statistics.domain.ShareClass;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import cn.thinkjoy.zgk.zgksystem.common.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 *
 * 日班级圈分享用户数
 *
 */
public interface IShareClassService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
    Page<ShareClass> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy);
    List<StatisticsPojo> queryByTime(Map<String,Object> map);
    Page<StatisticsPojo> queryByPageTime(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy, int queryType);
}

