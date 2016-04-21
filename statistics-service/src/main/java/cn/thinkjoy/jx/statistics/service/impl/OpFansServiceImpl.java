package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.jx.statistics.dao.IOpFansDAO;
import cn.thinkjoy.jx.statistics.domain.OpFans;
import cn.thinkjoy.jx.statistics.service.IOpFansService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 * 服务账户日粉丝数统计
 *
 */
@Service("OpFansServiceImpl")
public class OpFansServiceImpl extends AbstractPageService<IBaseDAO<OpFans>,OpFans> implements IOpFansService<IBaseDAO<OpFans>,OpFans> {
    @Autowired
    private IOpFansDAO opFansDAO;

    @Override
    public IBaseDAO<OpFans> getDao(){
        return opFansDAO;
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
    public Page<OpFans> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy) {
        Page<OpFans> page = new Page<>();
        Integer count = opFansDAO.totalCount(map);
        if (count > 0) {
            page.setCount(count);
            page.setList(opFansDAO.queryByPageAndCondition(map, offset, rows, orderBy, sortBy));
        }
        return page;
    }

    @Override
    public Page<OpFans> queryByPageAndClassfy(String dateTime, long classfyId, int offset, int rows, String orderBy, String sortBy) {
        Page<OpFans> page = new Page<>();
        int count = opFansDAO.countByPageAndClassfy(classfyId, dateTime);
        if (count > 0) {
            page.setCount(count);
            page.setList(opFansDAO.quertByPageAndClassfy(classfyId, dateTime, offset, rows, orderBy, sortBy));
        }
        return page;
    }
}