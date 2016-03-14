package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.domain.OpFans;

import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 * 服务账户日粉丝数统计
 *
 */
public interface IOpFansService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
    Page<OpFans> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy);

    Page<OpFans> queryByPageAndClassfy(String dateTime, long classfyId, int offset, int rows, String orderBy, String sortBy);
}


