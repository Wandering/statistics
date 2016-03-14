package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.dao.IUserGroupChatMemberDAO;
import cn.thinkjoy.jx.statistics.domain.UserGroupChatMember;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IUserGroupChatMemberService;
import cn.thinkjoy.jx.statistics.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/18.
 *
 * 日班级聊天人数汇总
 *
 */
@Service("UserGroupChatMemberServiceImpl")
public class UserGroupChatMemberServiceImpl extends AbstractPageService<IBaseDAO<UserGroupChatMember>,UserGroupChatMember> implements IUserGroupChatMemberService<IBaseDAO<UserGroupChatMember>,UserGroupChatMember> {
    @Autowired
    private IUserGroupChatMemberDAO userGroupChatMemberDAO;

    @Override
    public IBaseDAO<UserGroupChatMember> getDao(){
        return userGroupChatMemberDAO;
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
    public Page<UserGroupChatMember> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy) {
        Page<UserGroupChatMember> page = new Page<>();
        Integer count = userGroupChatMemberDAO.totalCount(map);
        if (count > 0) {
            page.setCount(count);
            page.setList(userGroupChatMemberDAO.queryByPageAndCondition(map, offset, rows, orderBy, sortBy));
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
        List<StatisticsPojo> list=userGroupChatMemberDAO.queryByTime(map);
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
            Integer count = userGroupChatMemberDAO.countByPageTime(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(userGroupChatMemberDAO.queryByPageTime(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }else if(queryType==Constants.WHERR_AREAID_QUERY_SCHOOL_NUM){
            Integer count = userGroupChatMemberDAO.countByPageTimeAndAreaId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(userGroupChatMemberDAO.queryByPageTimeAndAreaId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }

        }else {
            Integer count = userGroupChatMemberDAO.countByPageTimeAndSchoolId(map);
            if (count > 0) {
                page.setCount(count);
                page.setList(userGroupChatMemberDAO.queryByPageTimeAndSchoolId(map, offset, rows, orderBy, sortBy));
                return page;
            } else {
                throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
            }
        }

    }
}