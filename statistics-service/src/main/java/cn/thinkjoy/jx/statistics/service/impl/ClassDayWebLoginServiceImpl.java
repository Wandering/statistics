package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IClassDayLoginDAO;
import cn.thinkjoy.jx.statistics.dao.IClassDayWebLoginDAO;
import cn.thinkjoy.jx.statistics.domain.ClassDayLogin;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IClassDayLoginService;
import cn.thinkjoy.jx.statistics.service.IClassDayWebLoginService;
import cn.thinkjoy.jx.statistics.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by chenmeng on 16/2/19.
 *
 * 日班级登录人数汇总
 *
 */
@Service("ClassDayWebLoginServiceImpl")
public class ClassDayWebLoginServiceImpl extends AbstractPageService<IBaseDAO<ClassDayLogin>,ClassDayLogin> implements IClassDayWebLoginService<IBaseDAO<ClassDayLogin>,ClassDayLogin> {
    @Autowired
    private IClassDayWebLoginDAO classDayLoginDAO;

    @Override
    public IBaseDAO<ClassDayLogin> getDao(){
        return classDayLoginDAO;
    };

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
    public Page<ClassDayLogin> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy) {
        Page<ClassDayLogin> page = new Page<>();
        Integer count = classDayLoginDAO.totalCount(map);
        if (count > 0) {
            page.setCount(count);
            page.setList(classDayLoginDAO.queryByPageAndCondition(map, offset, rows, orderBy, sortBy));
            return page;
        } else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }
    /**
     *
     * @param map
     * @return
     */
    public List<StatisticsPojo> queryByTime(Map<String, Object> map) {
        List<StatisticsPojo> list=classDayLoginDAO.queryByTime(map);
        if(list!=null && list.size()>0){
            return  list;
        }else{
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
    public Page<StatisticsPojo> queryByPageTime(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy,int queryType) {
        Page<StatisticsPojo> page = new Page<>();
        if(queryType== Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM){
            Integer count = classDayLoginDAO.countByPageTime(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(classDayLoginDAO.queryByPageTime(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }else if(queryType==Constants.WHERR_AREAID_QUERY_SCHOOL_NUM){
            Integer count = classDayLoginDAO.countByPageTimeAndAreaId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(classDayLoginDAO.queryByPageTimeAndAreaId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }

        }else {
            Integer count = classDayLoginDAO.countByPageTimeAndSchoolId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(classDayLoginDAO.queryByPageTimeAndSchoolId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }

    }
}
