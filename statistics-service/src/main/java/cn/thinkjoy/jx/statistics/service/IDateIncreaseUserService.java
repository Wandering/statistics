package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.jx.statistics.domain.ParentIncreaseDetail;
import cn.thinkjoy.jx.statistics.domain.TeacherIncreaseDetail;
import cn.thinkjoy.jx.statistics.pojo.StatisticsPojo;
import cn.thinkjoy.zgk.zgksystem.common.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zl on 2015/12/16.
 */
public interface IDateIncreaseUserService {

    List<StatisticsPojo> queryByTime(Map<String,Object> map);

    Page<StatisticsPojo> queryByPageTime(Map<String,Object> map, int offset, int rows, String orderBy, String sortBy, int queryType);

    Page<ParentIncreaseDetail> queryParentIncreaseUserDetail(Map<String,Object> map,int offset,int rows,String sortBy);

    Page<TeacherIncreaseDetail> queryTeacherIncreaseUserDetail(Map<String,Object> map,int offset,int rows,String sortBy);

}
