package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IUserActiveDao;
import cn.thinkjoy.jx.statistics.domain.Billing;
import cn.thinkjoy.jx.statistics.domain.pojo.UserActiveInfo;
import cn.thinkjoy.jx.statistics.service.UserActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Mike on 2016/1/16.
 */
@Service("UserActiveServiceImpl")
public class UserActiveServiceImpl extends AbstractPageService<IBaseDAO<UserActiveInfo>, UserActiveInfo>  implements UserActiveService<IBaseDAO<UserActiveInfo>,UserActiveInfo> {
    @Autowired
    IUserActiveDao userActiveDao;
    @Override
    public Page<UserActiveInfo> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy) {
        Page<UserActiveInfo> page = new Page<>();
        Integer count = userActiveDao.countByPage();
        if (count > 0){
            page.setCount(count);
            page.setList( userActiveDao.queryByPage(map, offset, rows, orderBy, sortBy));
            return page;
        }else {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    @Override
    public IBaseDAO<UserActiveInfo> getDao() {
        return userActiveDao;
    }
}
