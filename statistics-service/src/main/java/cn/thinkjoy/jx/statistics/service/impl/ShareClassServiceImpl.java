package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IShareClassDAO;
import cn.thinkjoy.jx.statistics.domain.ShareClass;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IShareClassService;
import cn.thinkjoy.jx.statistics.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 *
 * 日班级圈分享用户数
 *
 */
@Service("ShareClassServiceImpl")
public class ShareClassServiceImpl extends AbstractPageService<IBaseDAO<ShareClass>,ShareClass> implements IShareClassService<IBaseDAO<ShareClass>,ShareClass> {
    @Autowired
    private IShareClassDAO shareClassDAO;

    @Override
    public IBaseDAO<ShareClass> getDao(){
        return shareClassDAO;
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
    public Page<ShareClass> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy) {
        Page<ShareClass> page = new Page<>();
        Integer count = shareClassDAO.totalCount(map);
        if (count > 0) {
            page.setCount(count);
            page.setList(shareClassDAO.queryByPageAndCondition(map, offset, rows, orderBy, sortBy));
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
        List<StatisticsPojo> list=shareClassDAO.queryByTime(map);
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
            Integer count = shareClassDAO.countByPageTime(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(shareClassDAO.queryByPageTime(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }else if(queryType==Constants.WHERR_AREAID_QUERY_SCHOOL_NUM){
            Integer count = shareClassDAO.countByPageTimeAndAreaId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(shareClassDAO.queryByPageTimeAndAreaId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }

        }else {
            Integer count = shareClassDAO.countByPageTimeAndSchoolId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(shareClassDAO.queryByPageTimeAndSchoolId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }

    }

}