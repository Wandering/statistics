/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: grab
 * $Id:  CardDAO.java 2016-03-15 17:46:13 $
 */
package cn.thinkjoy.agents.dao.ex;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ICardExDAO{
        /**
         * 增加排序支持
         * @param condition
         * @param offset
         * @param rows
         * @param orderBy
         * @param sortBy
         * @return
         */
        public List<Map<String,Object>> queryPage(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
                                                  @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);


        public List<Map<String,Object>>  queryPage(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
                                                   @Param("orderBy") String orderBy, @Param("sortBy") String sortBy, @Param("selector") Map<String, Object> selector);

        public int count(Map<String, Object> condition);

        public int output(Map<String, Object> condition);
}
