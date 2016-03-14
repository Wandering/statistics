package cn.thinkjoy.jx.statistics.dao;

import cn.thinkjoy.jx.statistics.domain.OpAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/10/13.
 */
public interface IOpStatisticsDao {

    List<Map<String,Object>> getClassfyInfo();

    List<OpAccount> getAccountInfoByClassfyId(@Param("classfyId")Long classfyId);

    List<Map<String,Object>> getAccountFans(@Param("userId")Long userId, @Param("dateStart")String dateStart,@Param("dateEnd") String dateEnd);

    List<Map<String,Object>> getAccountArticles(@Param("userId")Long userId, @Param("dateStart")String dateStart, @Param("dateEnd")String dateEnd);

    List<Map<String,Object>> getAccountEvents(@Param("userId")Long userId, @Param("dateStart")String dateStart,  @Param("dateEnd")String dateEnd);
}
