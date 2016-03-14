package cn.thinkjoy.jx.statistics.service.impl;

import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.jx.statistics.dao.IDateDecreaseUserDAO;
import cn.thinkjoy.jx.statistics.domain.pojo.StatisticsPojo;
import cn.thinkjoy.jx.statistics.service.IDateDecreaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 2016/1/25.
 */
@Service("DateDecreaseUserServiceImpl")
public class DateDecreaseUserServiceImpl implements IDateDecreaseUserService {
    @Autowired
    IDateDecreaseUserDAO dateDecreaseUserDAO;
    @Override
    public List<StatisticsPojo> queryByTime(Map<String, Object> map) {

        List<StatisticsPojo> list = dateDecreaseUserDAO.queryByTime(map);

        //ɾ��Ӱ�����ܵ��������
      /*  List<StatisticsPojo> listResult = new ArrayList<>();
        for(int i = 0;i<list.size();i++)
        {
            StatisticsPojo st = list.get(i);
            boolean iscontains = false;
            for(int j= 0 ;j<listResult.size();j++)
            {

                if(listResult.get(j).getDateDay().equals(list.get(i).getDateDay()))
                {
                    StatisticsPojo resultPojo = listResult.get(j);
                    resultPojo.setTeacherNum(resultPojo.getTeacherNum() + st.getTeacherNum());
                    resultPojo.setDelStudentNum(resultPojo.getDelStudentNum() + st.getDelStudentNum());
                    resultPojo.setGraduateStudentNum(resultPojo.getGraduateStudentNum() + st.getGraduateStudentNum());
                    iscontains = true;
                    break;
                }
            }
            if(!iscontains)
            {
                listResult.add(st);
            }
        }*/
        return list;
    }
    @Override
    public Page<StatisticsPojo> queryByAreaIds(Map<String, Object> map,int offset,int rows) {
        Page<StatisticsPojo> page = new Page<>();
        List<StatisticsPojo> resultList = dateDecreaseUserDAO.queryByAreaIds(map, offset, rows);
        page.setList(resultList);
        page.setCount(resultList.size());
        return page;
    }
    @Override
    public Page<StatisticsPojo> queryBySchoolIds(Map<String, Object> map,int offset,int rows) {
        Page<StatisticsPojo> page = new Page<>();
        List<StatisticsPojo> resultList = dateDecreaseUserDAO.queryBySchoolIds(map, offset, rows);
        page.setList(resultList);
        page.setCount(resultList.size());
        return page;
    }
    @Override
    public Page<StatisticsPojo> queryByClassIds(Map<String, Object> map,int offset,int rows) {
        Page<StatisticsPojo> page = new Page<>();
        List<StatisticsPojo> resultList = dateDecreaseUserDAO.queryByClassIds(map,offset,rows);
        page.setList(resultList);
        page.setCount(resultList.size());
        return page;
    }


}
