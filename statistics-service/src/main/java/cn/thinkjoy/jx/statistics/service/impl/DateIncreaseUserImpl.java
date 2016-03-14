package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IDateIncreaseUserDao;
import cn.thinkjoy.jx.statistics.domain.ParentIncreaseDetail;
import cn.thinkjoy.jx.statistics.domain.TeacherIncreaseDetail;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IDateIncreaseUserService;
import cn.thinkjoy.jx.statistics.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zl on 2015/12/16.
 */
@Service("DateIncreaseUserImpl")
public class DateIncreaseUserImpl implements IDateIncreaseUserService {
    @Autowired
    private IDateIncreaseUserDao dateIncreaseUserDao;

    /**
     *
     * @param map
     * @return
     */
    public List<StatisticsPojo> queryByTime(Map<String, Object> map) {
        List<StatisticsPojo> list = dateIncreaseUserDao.queryByTime(map);
        if (null!=list){
            return  list;
        } else
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
   }

    /**
     *根据时间区域学校班级查询日增长老师和家长数
     */
    public Page<StatisticsPojo> queryByPageTime(Map<String,Object> map,int offset ,int rows,String orderBy, String sortBy,int queryType){
        Page<StatisticsPojo> page = new Page<>();
        if (queryType == Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM){
            Integer count = dateIncreaseUserDao.countByPageTime(map);
            if (count>0){
                page.setCount(count);
                page.setList(dateIncreaseUserDao.queryByPageTime(map,offset,rows,orderBy,sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
            }
        } else if(queryType == Constants.WHERR_AREAID_QUERY_SCHOOL_NUM){
            Integer count = dateIncreaseUserDao.countByPageTimeAndAreaId(map);
            if (count>0){
                page.setCount(count);
                page.setList(dateIncreaseUserDao.queryByPageTimeAndAreaId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
            }
        } else {
            Integer count = dateIncreaseUserDao.countByPageTimeAndSchoolId(map);
            if (count>0){
                page.setCount(count);
                page.setList(dateIncreaseUserDao.queryByPageTimeAndSchoolId(map,offset,rows,orderBy,sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
            }
        }
    }
    /**
     * 根据时间班级查询日增长家长数
     */
    public Page<ParentIncreaseDetail> queryParentIncreaseUserDetail(Map<String,Object> map,int offset ,int rows, String sortBy){
        Page<ParentIncreaseDetail> page = new Page<>();
        Integer count = dateIncreaseUserDao.countParentIncreaseUserDetail(map);
        if (count>0){
            page.setCount(count);
            page.setList(dateIncreaseUserDao.queryParentIncreaseUserDetail(map,offset,rows,sortBy));
            return page;
        } else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
    }
    /**
     * 根据时间班级查询日增长老师数
     */
    public Page<TeacherIncreaseDetail> queryTeacherIncreaseUserDetail(Map<String,Object> map,int offset ,int rows, String sortBy){
        Page<TeacherIncreaseDetail> page = new Page<>();
        Integer count = dateIncreaseUserDao.countTeacherIncreaseUserDetail(map);
        if (count>0){
            page.setCount(count);
            page.setList(dateIncreaseUserDao.queryTeacherIncreaseUserDetail(map, offset, rows, sortBy));
            return page;
        } else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
    }
}
