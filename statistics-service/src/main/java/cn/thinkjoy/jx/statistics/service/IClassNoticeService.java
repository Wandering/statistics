package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.domain.ClassNotice;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 * 日班级发送作业公告教师数统计
 */
public interface IClassNoticeService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
    Page<ClassNotice> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy,int queryType);
    List<ClassNotice> queryByTime(Map<String,Object> map);
}