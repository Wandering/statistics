package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.jx.statistics.pojo.UserActiveInfo;
import cn.thinkjoy.zgk.zgksystem.common.Page;

import java.util.Map;

/**
 * Created by Mike on 2016/1/16.
 */
public interface UserActiveService <D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
    Page<UserActiveInfo> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy);
}
