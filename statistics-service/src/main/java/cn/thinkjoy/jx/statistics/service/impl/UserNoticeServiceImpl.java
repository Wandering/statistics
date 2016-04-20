package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IUserNoticeDAO;
import cn.thinkjoy.jx.statistics.domain.UserNotice;
import cn.thinkjoy.jx.statistics.service.IUserNoticeService;
import cn.thinkjoy.jx.statistics.util.Constants;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 *
 * 日班级教师发送作业公告个数汇总
 *
 */
@Service("UserNoticeServiceImpl")
public class UserNoticeServiceImpl extends AbstractPageService<IBaseDAO<UserNotice>,UserNotice> implements IUserNoticeService<IBaseDAO<UserNotice>,UserNotice> {
    @Autowired
    private IUserNoticeDAO userNoticeDAO;

    @Override
    public IBaseDAO<UserNotice> getDao(){
        return userNoticeDAO;
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
    public Page<UserNotice> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy, int queryType) {
        Page<UserNotice> page = new Page<>();
        if(queryType== Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM){
            Integer count = userNoticeDAO.countByPageTime(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(userNoticeDAO.queryByPageTime(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }else if(queryType==Constants.WHERR_AREAID_QUERY_SCHOOL_NUM){
            Integer count = userNoticeDAO.countByPageTimeAndAreaId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(userNoticeDAO.queryByPageTimeAndAreaId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }

        }else {
            Integer count = userNoticeDAO.countByPageTimeAndSchoolId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(userNoticeDAO.queryByPageTimeAndSchoolId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }

    }

    /**
     *
     * @param map
     * @return
     */
    public List<UserNotice> queryByTime(Map<String, Object> map) {
        List<UserNotice> list=userNoticeDAO.queryByTime(map);
        if(list!=null && list.size()>0){
            return  list;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }
}