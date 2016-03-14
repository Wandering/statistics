package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IClassNoticeDAO;
import cn.thinkjoy.jx.statistics.domain.ClassNotice;
import cn.thinkjoy.jx.statistics.service.IClassNoticeService;
import cn.thinkjoy.jx.statistics.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 * 日班级发送作业公告教师数统计
 */
@Service("ClassNoticeServiceImpl")
public class ClassNoticeServiceImpl extends AbstractPageService<IBaseDAO<ClassNotice>,ClassNotice> implements IClassNoticeService<IBaseDAO<ClassNotice>,ClassNotice> {
    @Autowired
    private IClassNoticeDAO classNoticeDAO;

    @Override
    public IBaseDAO<ClassNotice> getDao(){
        return classNoticeDAO;
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
    public Page<ClassNotice> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy,int queryType) {
        Page<ClassNotice> page = new Page<>();
        if(queryType== Constants.BETWEEN_DATEDAY_QUERY_AREA_NUM){
            Integer count = classNoticeDAO.countByPageTime(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(classNoticeDAO.queryByPageTime(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }else if(queryType==Constants.WHERR_AREAID_QUERY_SCHOOL_NUM){
            Integer count = classNoticeDAO.countByPageTimeAndAreaId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(classNoticeDAO.queryByPageTimeAndAreaId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }

        }else {
            Integer count = classNoticeDAO.countByPageTimeAndSchoolId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(classNoticeDAO.queryByPageTimeAndSchoolId(map, offset, rows, orderBy, sortBy));
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
    public List<ClassNotice> queryByTime(Map<String, Object> map) {
        List<ClassNotice> list=classNoticeDAO.queryByTime(map);
        if(list!=null && list.size()>0){
            return  list;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }
}