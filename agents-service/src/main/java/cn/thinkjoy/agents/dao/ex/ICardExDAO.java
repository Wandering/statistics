/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: grab
 * $Id:  CardDAO.java 2016-03-15 17:46:13 $
 */
package cn.thinkjoy.agents.dao.ex;

import cn.thinkjoy.agents.dao.ex.common.BaseCommonExDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ICardExDAO extends BaseCommonExDAO{


        public int output(Map<String, Object> condition);
        public int active(Map<String, Object> condition);
        public Map<String,Object> selectByUid(String uid);
        public String selectCardByUid(String cardNumber);
}
